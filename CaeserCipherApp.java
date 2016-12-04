
public class CaeserCipherApp
{
	public static void main( String arguments[] )
	{
		CaeserCipher myCaeserCipher = new CaeserCipher("Caeser Cipher Encryption") ;

		myCaeserCipher.pack() ;

		myCaeserCipher.setSize( new java.awt.Dimension( 800, 600 )) ;

		myCaeserCipher.setVisible( true ) ;
	}
}
