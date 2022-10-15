import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    List<String> strsList = new ArrayList<>();
    String str = "";


    public String handleRequest(URI url) throws IOException {
        if (url.getPath().equals("/")) {
            return String.format("Number: %d", num);
        } else if (url.getPath().equals("/increment")) {
            num += 1;
            return String.format("Number incremented!");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    strsList.add(parameters[1]);
                }
                return parameters[1];
            }
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                str = "";
                for (String s: strsList) {
                    if (s.contains(parameters[1])) {
                        str += s + " ";
                    }
                }
                return str;
            }
            if (url.getPath().contains("/filesearch")) {
                List<File> files = FileGetter.getFiles(new File("TestFiles/"));
                str = "";
                String[] parameters = url.getQuery().split("=");
                for (File f: files) {
                    String name = f.getName();
                    if (name.contains(parameters[1])) {
                        if (!str.contains(name)) {
                            str += name + " ";
                        }
                    }
                }
                return str;
            }
            return "404 Not Found!";
        }
    }
}
class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }    
}
