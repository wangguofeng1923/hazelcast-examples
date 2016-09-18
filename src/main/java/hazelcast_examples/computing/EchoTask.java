package hazelcast_examples.computing;

import java.io.Serializable;

public class EchoTask implements Runnable, Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private final String msg;

  public EchoTask( String msg ) {
    this.msg = msg;
  }

  @Override
  public void run() {
    try {
      Thread.sleep( 5000 );
    } catch ( InterruptedException e ) {
    }
    System.out.println( "echo:" + msg );
  }
}