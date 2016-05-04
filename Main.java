
import static spark.Spark.get;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import utils.GA;

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    get("/phonenumber", (req, res) -> {
      res.type("text/xml");
      GA.sendToGa("finished", "Finished" + req.queryParams("sound"));
      if (req.queryParams("Digits") == null || req.queryParams("Digits").equals("0")) {
        return "<Response> <Say voice='alice'> Returning to main menu.</Say><Redirect method='GET'>/menu</Redirect></Response>";
      }
      else {
        int choice = Integer.parseInt(req.queryParams("sound"));
        
        if (choice < 1 || choice > 3) {
          return "<Response> <Say voice='alice'>We're sorry, we could not find that story. Returning to main menu.</Say><Redirect method='GET'>/menu</Redirect></Response>";
        }
        String callerNumber = req.queryParams("From");
        String title = "download" + choice;
        String page = "Download" + choice;
        String message = "<Response><Sms from='+14803728927' to='" + callerNumber + "'>story " + choice + " link</Sms></Response>";
        GA.sendToGa(title, page);
        return message;
      }
    });

    get("/afterstory", (req, res) -> {
      res.type("text/xml");
      return "<Response> <Gather timeout='20' action='/phonenumber?sound=" + req.queryParams("sound") + "' method='GET' numDigits='1'>"
          + "<Say voice='alice'> Did you like this story? If yes, press one, and we'll text you a link to the story. Otherwise, press 0 to go back to the main menu.</Say>"
          + "</Gather><Redirect method='GET'>/phonenumber?sound=" + req.queryParams("sound") + "</Redirect></Response>";
    });

    get("/sounds", (req, res) -> {
      res.type("text/xml"); 
      if (req.queryParams("Digits") == null) {
        return "<Response> <Say voice='alice'> Sorry, try again.</Say><Redirect method='GET'>/menu</Redirect></Response>";
      }
      switch(req.queryParams("Digits")) {
          case "1":
              GA.sendToGa("pachebel", "Pachebel");
              return "<Response><Play>https://s3.amazonaws.com/mh-stories-storage/Pachelbel+-+Canon+in+D+Major.mp3</Play>"
                  + "<Redirect method='GET'>/afterstory?sound=1</Redirect></Response>";
          case "2":
              GA.sendToGa("recital", "Recital");
              return "<Response><Play>https://s3.amazonaws.com/mh-stories-storage/recit.mp3</Play>"
                  + "<Redirect method='GET'>/afterstory?sound=2</Redirect></Response>";
          case "3":
              GA.sendToGa("dial tone", "Dial Tone");
              return "<Response><Play>https://s3.amazonaws.com/mh-stories-storage/tone.mp3</Play>"
                  + "<Redirect method='GET'>/afterstory?sound=3</Redirect></Response>";
          default:
              return "<Response> <Say voice='alice'> Sorry, try again.</Say><Redirect method='GET'>/menu</Redirect></Response>";
      }
    });
    get("/menu", (req, res) -> {
      res.type("text/xml"); 
      GA.sendToGa("home", "Home");
      return "<Response>  <Gather timeout='20' action='/sounds' method='GET' numDigits='1'>"
          + "<Say voice='alice'> Welcome. For Pachebel, press 1. For recital, press 2. For dial tone, press 3.</Say>"
          + " </Gather></Response>";
    });
  }
}