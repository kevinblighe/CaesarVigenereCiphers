
public class VigenereApp
{
	public static void main( String arguments[] )
	{
		Vigenere myVigenere = new Vigenere("Vigenere Cipher Example") ;

		myVigenere.pack() ;

		myVigenere.setSize( new java.awt.Dimension( 800, 600 )) ;

		myVigenere.setVisible( true ) ;
	}
}
