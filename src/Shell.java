import java.io.*;
import java.util.*;
class Shell extends Thread {
    public Shell( ) {
    }
    public void run( ) {
        for ( int line = 1; ; line++ ) {
            String cmdLine = "";
            do {
                StringBuffer inputBuf = new StringBuffer( );
                SysLib.cerr( "shell[" + line + "]% " );
                SysLib.cin( inputBuf );
                cmdLine = inputBuf.toString( );
            } while ( cmdLine.length( ) == 0 );
            String[] args = SysLib.stringToArgs( cmdLine );
            int first = 0;
            for ( int i = 0; i < args.length; i++ ) {
                if ( args[i].equals( ";" ) || args[i].equals( "&" )
                        || i == args.length - 1 ) {
                    String[] command = generateCmd( args, first, ( i==args.length - 1 ) ? i+1 : i );
                    if ( command != null ) {
                        if (command[0].equals("exit")) {
                            SysLib.cout("Exiting");
                            SysLib.exit();
                        }
                        // check if command[0] is "exit". If so, get terminated
                        // otherwise, pass command to SysLib.exec( )
                        // if args[i]="&" don’t call SysLib.join( ), Otherwise (i.e., ";"), keep calling SysLib.join( )

                    }
                    first = i + 1; // go on to the next command delimited by ";" or "&"
                }
            }
        }
    }
    public static void main(String[] args) {

        Shell shell = new Shell();
        shell.run();

    }

    public String[] generateCmd(String[] args, int first, int passed) {
        int length = passed-first + 1;
        String[] command = new String[length];
        int count = 0;
        for (int i = first; i < passed; i++) {
            command[count] = args[i];
            count++;
        }
        return command;
    }
}
