package FormHandle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.ContentHandler;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;

import Activity.Login;

public class Form {
    private static String personType = "0";           //0-我是学生,  1-我是家长。
    private static String userNumber;
    private static String password;      //你的密码
    private static String verifyCode;            //验证码默认设置为空，后续通过用户的输入来填充这个值

    final static String loginHomePage = "http://kdjw.hnust.edu.cn:8080/kdjw/Logon.do?method=logon";
    final static String verifyImgSrcURL = "http://kdjw.hnust.edu.cn:8080/kdjw/verifycode.servlet";
    final static String getTableBaseURL = "http://kdjw.hnust.edu.cn:8080/kdjw/tkglAction.do?method=goListKbByXs";
    private final static String skipScript =  "<script language='javascript'>window.location.href='http://kdjw.hnust.edu.cn:8080/kdjw/framework/main.jsp';</script>";
    final static String cookieName = "JSESSIONID";
    //request parameters [personType, username, password, RANDOMCODE]
    private static Map<String, String> reqData ;
    private static Map<String, String> cookies =new HashMap<>();

    public Form(){ }
    public void submit(Context context,String user,String PWD,String codeStr){
        new Thread(()->{
        userNumber =user;
        password=PWD;
        verifyCode=codeStr;
        //赋值
        reqData = new HashMap<>();
        reqData.put("personType",personType);
        reqData.put("USERNAME",userNumber);
        reqData.put("PASSWORD",password);
        reqData.put("RANDOMCODE",verifyCode);

        try{
            Connection loginconn =null;
            Connection.Response loginResponse = null;
            loginconn =Jsoup.connect(loginHomePage);
            loginconn.cookies(cookies);
            loginconn.data(reqData);
            Log.e("Cookie",cookies.get(cookieName));
            //建立连接，返回
            loginResponse = loginconn.execute();
            Log.e("Successful",loginResponse.body());
            if(loginResponse.body().contains(skipScript))
                Log.e("Successful","登陆成功啦");
        }catch (Exception e){
            Log.e("EEEEERRRRRR", e.toString());
        }
        }).start();
    }
    public void getVerifyCodeImage(Context context){
        new Thread(()->{
            Bitmap verifyImage = null;
            try {
                Connection conn = Jsoup.connect(verifyImgSrcURL).ignoreContentType(true);
                Connection.Response response = null;
                response = conn.execute();
                cookies = response.cookies();//get cookie
                //cookies.put(cookieName, response.cookies().toString());
                Log.e("Cookie, Img", cookies.toString() );
                verifyImage = BitmapFactory.decodeStream( response.bodyStream() );      //get image
            } catch (Exception e) {
                Log.e("验证码失败：",e.toString());
            }
            ((Login)context).loadImage(verifyImage);
        }).start();
    }
}
