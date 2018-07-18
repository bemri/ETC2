package emribalazs.hu.etc;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class RiddleXMLParser
{
    private static final String TAG = "myapp";
    private Context mContext;

    public RiddleXMLParser(Context context){
        mContext = context;
    }

    public ArrayList<Riddle> readRiddles (int resourceID)throws XmlPullParserException, IOException
    {
        ArrayList<Riddle> listOfRiddles = new ArrayList<>();
        double coordinateV = 0;
        double coordinateV1 = 0;
        Riddle currentRiddle = new Riddle();
        ArrayList<String> listOfChoices = new ArrayList<>();

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput(new StringReader(readFileFromRawDirectory(resourceID)));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                String tag = xpp.getName();
                switch (tag){
                   case "coordinateV":
                       eventType = xpp.next();
                       coordinateV = Double.parseDouble(xpp.getText());
                       break;
                    case "coordinateV1":
                        eventType = xpp.next();
                        coordinateV1 = Double.parseDouble(xpp.getText());
                        LatLng coordinates = new LatLng(coordinateV,coordinateV1);
                        currentRiddle.setCoordinates(coordinates);
                        break;
                    case "name":
                        eventType = xpp.next();
                        currentRiddle.setName(xpp.getText());
                        break;
                    case "question":
                        eventType = xpp.next();
                        currentRiddle.setQuestion(xpp.getText());
                        break;
                    case "answer":
                        eventType = xpp.next();
                        currentRiddle.setAnswer(xpp.getText());
                        break;
                    case "type":
                        eventType = xpp.next();
                        currentRiddle.setType(Short.parseShort(xpp.getText()));
                        break;
                    case "zoom":
                        eventType = xpp.next();
                        currentRiddle.setZoom(Float.parseFloat(xpp.getText()));
                        break;
                    case "maxFailures":
                        eventType = xpp.next();
                        currentRiddle.setMaxFailures(Integer.parseInt((xpp.getText())));
                        break;
                    case "choice":
                        eventType = xpp.next();
                        listOfChoices.add(xpp.getText());
                        break;
                    default:
                        break;
               }
            }

            if(eventType == XmlPullParser.END_TAG) {
                if(xpp.getName().equals("choiceList")) {
                    currentRiddle.setChoices(listOfChoices);
                    listOfChoices = new ArrayList<>();
                }
                if(xpp.getName().equals("riddle")) {
                    listOfRiddles.add(currentRiddle);
                    currentRiddle = new Riddle();
                }
            }
            eventType = xpp.next();
        }
        return listOfRiddles;
    }

    private String readFileFromRawDirectory(int resourceId){
        InputStream iStream = mContext.getResources().openRawResource(resourceId);
        ByteArrayOutputStream byteStream = null;
        try {
            byte[] buffer = new byte[iStream.available()];
            iStream.read(buffer);
            byteStream = new ByteArrayOutputStream();
            byteStream.write(buffer);
            byteStream.close();
            iStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteStream.toString();
    }
}