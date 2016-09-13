/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.logging;

import com.hazelcast.instance.BuildInfo;
import com.hazelcast.instance.MemberImpl;
import com.hazelcast.logging.Slf4jFactory.Slf4jLogger;
import com.hazelcast.util.ConstructorFunction;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.slf4j.spi.LocationAwareLogger;

import static com.hazelcast.util.ConcurrencyUtil.getOrPutIfAbsent;

public class LoggingServiceImpl implements LoggingService {

    private final String groupName;
    private final CopyOnWriteArrayList<LogListenerRegistration> listeners
            = new CopyOnWriteArrayList<LogListenerRegistration>();

    private final ConcurrentMap<String, ILogger> mapLoggers = new ConcurrentHashMap<String, ILogger>(100);

    private final ConstructorFunction<String, ILogger> loggerConstructor
            = new ConstructorFunction<String, ILogger>() {

        @Override
        public ILogger createNew(String key) {
            return new DefaultLogger(key);
        }
    };

    private final LoggerFactory loggerFactory;
    private final BuildInfo buildInfo;

    private volatile MemberImpl thisMember = new MemberImpl();
    private volatile String thisAddressString = "[LOCAL]";
    private volatile Level minLevel = Level.OFF;

    public LoggingServiceImpl(String groupName, String loggingType, BuildInfo buildInfo) {
        this.groupName = groupName;
        this.loggerFactory = Logger.newLoggerFactory(loggingType);
        this.buildInfo = buildInfo;
    }

    public void setThisMember(MemberImpl thisMember) {
        this.thisMember = thisMember;
        this.thisAddressString = "[" + thisMember.getAddress().getHost() + "]:" + thisMember.getAddress().getPort();
    }

    @Override
    public ILogger getLogger(String name) {
        return getOrPutIfAbsent(mapLoggers, name, loggerConstructor);
    }

    @Override
    public ILogger getLogger(Class clazz) {
        return getOrPutIfAbsent(mapLoggers, clazz.getName(), loggerConstructor);
    }

    @Override
    public void addLogListener(Level level, LogListener logListener) {
        listeners.add(new LogListenerRegistration(level, logListener));
        if (level.intValue() < minLevel.intValue()) {
            minLevel = level;
        }
    }

    @Override
    public void removeLogListener(LogListener logListener) {
        listeners.remove(new LogListenerRegistration(Level.ALL, logListener));
    }

    void handleLogEvent(LogEvent logEvent) {
        for (LogListenerRegistration logListenerRegistration : listeners) {
            if (logEvent.getLogRecord().getLevel().intValue() >= logListenerRegistration.getLevel().intValue()) {
                logListenerRegistration.getLogListener().log(logEvent);
            }
        }
    }

    private static class LogListenerRegistration {
        final Level level;
        final LogListener logListener;

        LogListenerRegistration(Level level, LogListener logListener) {
            this.level = level;
            this.logListener = logListener;
        }

        public Level getLevel() {
            return level;
        }

        public LogListener getLogListener() {
            return logListener;
        }

        /**
         * True if LogListeners are equal.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            LogListenerRegistration other = (LogListenerRegistration) obj;
            if (logListener == null) {
                if (other.logListener != null) {
                    return false;
                }
            } else if (!logListener.equals(other.logListener)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return logListener != null ? logListener.hashCode() : 0;
        }
    }

    private class DefaultLogger extends AbstractLogger {


        transient LocationAwareLogger logger;
    	String FQCN = AbstractLogger.class.getName();
    	public DefaultLogger(String name) {
    		Slf4jLogger slf4jLogger=(Slf4jLogger)loggerFactory.getLogger(name);
    		 LocationAwareLogger logger=(LocationAwareLogger)(slf4jLogger.getLogger());
    		
    		this.logger=logger;
    		 FQCN=this.logger.getClass().getName();
    	}
    	
    	public void finest(String message) {
    		this.log(Level.FINEST, message);
    	}

    	public void finest(String message, Throwable thrown) {
    		this.log(Level.FINEST, message, thrown);
    	}

    	public void finest(Throwable thrown) {
    		this.log(Level.FINEST, thrown.getMessage(), thrown);
    	}

    	public void fine(String message) {
    		this.log(Level.FINE, message);
    	}

    	public boolean isFinestEnabled() {
    		return this.isLoggable(Level.FINEST);
    	}

    	public boolean isFineEnabled() {
    		return this.isLoggable(Level.FINE);
    	}

    	public void info(String message) {
    		this.log(Level.INFO, message);
    	}

    	public void severe(String message) {
    		this.log(Level.SEVERE, message);
    	}

    	public void severe(Throwable thrown) {
    		this.log(Level.SEVERE, thrown.getMessage(), thrown);
    	}

    	public void severe(String message, Throwable thrown) {
    		this.log(Level.SEVERE, message, thrown);
    	}

    	public void warning(String message) {
    		this.log(Level.WARNING, message);
    	}

    	public void warning(Throwable thrown) {
    		this.log(Level.WARNING, thrown.getMessage(), thrown);
    	}

    	public void warning(String message, Throwable thrown) {
    		this.log(Level.WARNING, message, thrown);
    	}
    	

    	@Override
    	public Level getLevel() {

    		
    		return this.logger.isTraceEnabled() ? Level.FINEST
    				: (this.logger.isDebugEnabled() ? Level.FINE
    						: (this.logger.isInfoEnabled() ? Level.INFO
    								: (this.logger.isWarnEnabled() ? Level.WARNING
    										: (this.logger.isErrorEnabled() ? Level.SEVERE : Level.OFF))));
    		
    	}

    	

    	@Override
    	public boolean isLoggable(Level level) {
    		return level == Level.FINEST ? this.logger.isTraceEnabled()
    				: (level == Level.FINER ? this.logger.isDebugEnabled()
    						: (level == Level.FINE ? this.logger.isDebugEnabled()
    								: (level == Level.CONFIG ? this.logger.isInfoEnabled()
    										: (level == Level.INFO ? this.logger.isInfoEnabled()
    												: (level == Level.WARNING ? this.logger.isWarnEnabled()
    														: (level == Level.SEVERE ? this.logger.isErrorEnabled()
    																: level != Level.OFF
    																		&& this.logger.isInfoEnabled()))))));
    	}

    	@Override
    	public void log(LogEvent logEvent) {
    		LogRecord logRecord = logEvent.getLogRecord();
    		Level level = logEvent.getLogRecord().getLevel();
    		String message = logRecord.getMessage();
    		Throwable thrown = logRecord.getThrown();
    		this.log(level, message, thrown);
    	}

    	
    	public void log(Level level, String message) {
    		/*if (level == Level.FINEST) {
    			this.logger.trace(message);
    			
    		} else if (level == Level.FINER || level == Level.FINE) {
    			this.logger.debug(message);
    		} else if (level == Level.CONFIG || level == Level.INFO) {
    			this.logger.info(message);
    		} else if (level == Level.WARNING) {
    			this.logger.warn(message);
    		} else if (level == Level.SEVERE) {
    			this.logger.error(message);
    		} else if (level != Level.OFF) {
    			this.logger.info(message);
    		}*/
    		this.log(level, message, null);
    	}

    	public void log(Level level, String message, Throwable t) {
    		if (level == Level.FINEST) {
    			this.logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, String.valueOf(message), null, t);
    		} else if (level == Level.FINER || level == Level.FINE) {
    			this.logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, String.valueOf(message), null, t);
    		} else if (level == Level.CONFIG || level == Level.INFO) {
    			this.logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, String.valueOf(message), null, t);
    		} else if (level == Level.WARNING) {
    			this.logger.log(null, FQCN, LocationAwareLogger.WARN_INT, String.valueOf(message), null, t);
    		} else if (level == Level.SEVERE) {
    			this.logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(message), null, t);
    		} else if (level != Level.OFF) {
    			this.logger.log(null, FQCN, LocationAwareLogger.INFO_INT, String.valueOf(message), null, t);
    		}
    	}
    }
}
