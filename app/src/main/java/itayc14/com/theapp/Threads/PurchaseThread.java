package com.itayc14.theapp.Threads;

import android.os.Handler;
import android.widget.Toast;
import com.itayc14.theapp.FireBaseManager;
import com.itayc14.theapp.purchaseStuff.PurchaseActivity;
import com.itayc14.theapp.purchaseStuff.PurchaseActivity.SuccessPoper;
import com.itayc14.theapp.purchaseStuff.PurchaseActivity.buyBtnEnabler;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PurchaseThread
        extends Thread
{
    private PurchaseActivity.buyBtnEnabler btnEnabler;
    private String eventID;
    private FireBaseManager fireManager;
    private Handler handler;
    private final HashMap<String, String> params;
    private PurchaseActivity.SuccessPoper popper;
    private PurchaseActivity purchaseActivity;
    private int ticketsAmount;

    public PurchaseThread(HashMap<String, String> paramHashMap, int paramInt, String paramString, PurchaseActivity paramPurchaseActivity, PurchaseActivity.SuccessPoper paramSuccessPoper, PurchaseActivity.buyBtnEnabler parambuyBtnEnabler)
    {
        this.params = paramHashMap;
        this.ticketsAmount = paramInt;
        this.eventID = paramString;
        this.purchaseActivity = paramPurchaseActivity;
        this.popper = paramSuccessPoper;
        this.handler = new Handler();
        this.btnEnabler = parambuyBtnEnabler;
        this.fireManager = new FireBaseManager();
    }

    private Map<String, String> converteFromPOSTToMap(String paramString)
    {
        HashMap localHashMap = new HashMap();
        StringBuilder localStringBuilder = new StringBuilder();
        String str1 = "";
        String str2 = "";
        int i = 0;
        int j = 0;
        int k = 0;
        if (k < paramString.length())
        {
            if (paramString.charAt(k) == '=')
            {
                k++;
                i = 1;
                str1 = localStringBuilder.toString();
                localStringBuilder = new StringBuilder();
            }
            for (;;)
            {
                localStringBuilder.append(paramString.charAt(k));
                if ((i != 0) || (j != 0))
                {
                    localHashMap.put(str1, str2);
                    i = 0;
                    j = 0;
                }
                k++;
                break;
                if (paramString.charAt(k) == '&')
                {
                    k++;
                    j = 1;
                    str2 = localStringBuilder.toString();
                    localStringBuilder = new StringBuilder();
                }
            }
        }
        return localHashMap;
    }

    private String queryParams(HashMap<String, String> paramHashMap)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        int i = 1;
        Iterator localIterator = paramHashMap.keySet().iterator();
        if (localIterator.hasNext())
        {
            String str = (String)localIterator.next();
            if (i != 0) {
                i = 0;
            }
            for (;;)
            {
                try
                {
                    localStringBuilder.append(URLEncoder.encode(str, "UTF-8"));
                    localStringBuilder.append("=");
                    localStringBuilder.append(URLEncoder.encode((String)paramHashMap.get(str), "UTF-8"));
                }
                catch (UnsupportedEncodingException localUnsupportedEncodingException)
                {
                    localUnsupportedEncodingException.printStackTrace();
                }
                break;
                localStringBuilder.append("&");
            }
        }
        return localStringBuilder.toString();
    }

    /* Error */
    public void run()
    {
        // Byte code:
        //   0: invokestatic 139	com/itayc14/theapp/Threads/LockSeatsForFewMinThread:getCurrentThread	()Lcom/itayc14/theapp/Threads/LockSeatsForFewMinThread;
        //   3: astore_1
        //   4: aconst_null
        //   5: astore_2
        //   6: aconst_null
        //   7: astore_3
        //   8: aconst_null
        //   9: astore 4
        //   11: new 141	java/net/URL
        //   14: dup
        //   15: ldc 143
        //   17: invokespecial 146	java/net/URL:<init>	(Ljava/lang/String;)V
        //   20: invokevirtual 150	java/net/URL:openConnection	()Ljava/net/URLConnection;
        //   23: checkcast 152	javax/net/ssl/HttpsURLConnection
        //   26: astore_2
        //   27: aload_2
        //   28: sipush 15000
        //   31: invokevirtual 156	javax/net/ssl/HttpsURLConnection:setConnectTimeout	(I)V
        //   34: aload_2
        //   35: sipush 10000
        //   38: invokevirtual 159	javax/net/ssl/HttpsURLConnection:setReadTimeout	(I)V
        //   41: aload_2
        //   42: ldc 161
        //   44: invokevirtual 164	javax/net/ssl/HttpsURLConnection:setRequestMethod	(Ljava/lang/String;)V
        //   47: aload_2
        //   48: iconst_0
        //   49: invokevirtual 168	javax/net/ssl/HttpsURLConnection:setUseCaches	(Z)V
        //   52: aload_2
        //   53: iconst_1
        //   54: invokevirtual 171	javax/net/ssl/HttpsURLConnection:setDoOutput	(Z)V
        //   57: aload_2
        //   58: iconst_1
        //   59: invokevirtual 174	javax/net/ssl/HttpsURLConnection:setDoInput	(Z)V
        //   62: aload_2
        //   63: invokevirtual 178	javax/net/ssl/HttpsURLConnection:getOutputStream	()Ljava/io/OutputStream;
        //   66: astore_3
        //   67: ldc 180
        //   69: new 57	java/lang/StringBuilder
        //   72: dup
        //   73: invokespecial 58	java/lang/StringBuilder:<init>	()V
        //   76: ldc 182
        //   78: invokevirtual 119	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   81: aload_0
        //   82: aload_0
        //   83: getfield 28	com/itayc14/theapp/Threads/PurchaseThread:params	Ljava/util/HashMap;
        //   86: invokespecial 184	com/itayc14/theapp/Threads/PurchaseThread:queryParams	(Ljava/util/HashMap;)Ljava/lang/String;
        //   89: invokevirtual 119	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   92: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   95: invokestatic 190	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
        //   98: pop
        //   99: aload_3
        //   100: aload_0
        //   101: aload_0
        //   102: getfield 28	com/itayc14/theapp/Threads/PurchaseThread:params	Ljava/util/HashMap;
        //   105: invokespecial 184	com/itayc14/theapp/Threads/PurchaseThread:queryParams	(Ljava/util/HashMap;)Ljava/lang/String;
        //   108: invokevirtual 194	java/lang/String:getBytes	()[B
        //   111: invokevirtual 200	java/io/OutputStream:write	([B)V
        //   114: aload_2
        //   115: invokevirtual 204	javax/net/ssl/HttpsURLConnection:getInputStream	()Ljava/io/InputStream;
        //   118: astore 4
        //   120: sipush 1024
        //   123: newarray byte
        //   125: astore 13
        //   127: new 57	java/lang/StringBuilder
        //   130: dup
        //   131: invokespecial 58	java/lang/StringBuilder:<init>	()V
        //   134: astore 14
        //   136: aload 4
        //   138: aload 13
        //   140: invokevirtual 210	java/io/InputStream:read	([B)I
        //   143: istore 15
        //   145: iload 15
        //   147: ifle +84 -> 231
        //   150: aload 14
        //   152: new 62	java/lang/String
        //   155: dup
        //   156: aload 13
        //   158: iconst_0
        //   159: iload 15
        //   161: invokespecial 213	java/lang/String:<init>	([BII)V
        //   164: invokevirtual 119	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   167: pop
        //   168: goto -32 -> 136
        //   171: astore 8
        //   173: aload 8
        //   175: invokevirtual 214	java/io/IOException:printStackTrace	()V
        //   178: ldc 180
        //   180: new 57	java/lang/StringBuilder
        //   183: dup
        //   184: invokespecial 58	java/lang/StringBuilder:<init>	()V
        //   187: ldc 216
        //   189: invokevirtual 119	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   192: aload 8
        //   194: invokevirtual 219	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   197: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   200: invokestatic 190	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
        //   203: pop
        //   204: aload 4
        //   206: ifnull +8 -> 214
        //   209: aload 4
        //   211: invokevirtual 222	java/io/InputStream:close	()V
        //   214: aload_3
        //   215: ifnull +7 -> 222
        //   218: aload_3
        //   219: invokevirtual 223	java/io/OutputStream:close	()V
        //   222: aload_2
        //   223: ifnull +7 -> 230
        //   226: aload_2
        //   227: invokevirtual 226	javax/net/ssl/HttpsURLConnection:disconnect	()V
        //   230: return
        //   231: aload_0
        //   232: aload 14
        //   234: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   237: invokespecial 228	com/itayc14/theapp/Threads/PurchaseThread:converteFromPOSTToMap	(Ljava/lang/String;)Ljava/util/Map;
        //   240: astore 17
        //   242: aload 17
        //   244: ldc 230
        //   246: ldc 232
        //   248: invokeinterface 84 3 0
        //   253: pop
        //   254: aload 17
        //   256: ldc 230
        //   258: invokeinterface 233 2 0
        //   263: checkcast 62	java/lang/String
        //   266: ldc 232
        //   268: invokevirtual 237	java/lang/String:equals	(Ljava/lang/Object;)Z
        //   271: ifeq +58 -> 329
        //   274: aload_1
        //   275: iconst_1
        //   276: invokevirtual 240	com/itayc14/theapp/Threads/LockSeatsForFewMinThread:setIsKillingThreadNeeded	(Z)V
        //   279: aload_0
        //   280: getfield 48	com/itayc14/theapp/Threads/PurchaseThread:fireManager	Lcom/itayc14/theapp/FireBaseManager;
        //   283: aload_0
        //   284: getfield 30	com/itayc14/theapp/Threads/PurchaseThread:ticketsAmount	I
        //   287: aload_0
        //   288: getfield 32	com/itayc14/theapp/Threads/PurchaseThread:eventID	Ljava/lang/String;
        //   291: aload_0
        //   292: getfield 34	com/itayc14/theapp/Threads/PurchaseThread:purchaseActivity	Lcom/itayc14/theapp/purchaseStuff/PurchaseActivity;
        //   295: aload_0
        //   296: getfield 36	com/itayc14/theapp/Threads/PurchaseThread:popper	Lcom/itayc14/theapp/purchaseStuff/PurchaseActivity$SuccessPoper;
        //   299: invokevirtual 244	com/itayc14/theapp/FireBaseManager:addToEventGuestList	(ILjava/lang/String;Landroid/app/Activity;Lcom/itayc14/theapp/purchaseStuff/PurchaseActivity$SuccessPoper;)V
        //   302: aload 4
        //   304: ifnull +8 -> 312
        //   307: aload 4
        //   309: invokevirtual 222	java/io/InputStream:close	()V
        //   312: aload_3
        //   313: ifnull +7 -> 320
        //   316: aload_3
        //   317: invokevirtual 223	java/io/OutputStream:close	()V
        //   320: aload_2
        //   321: ifnull -91 -> 230
        //   324: aload_2
        //   325: invokevirtual 226	javax/net/ssl/HttpsURLConnection:disconnect	()V
        //   328: return
        //   329: aload_0
        //   330: getfield 43	com/itayc14/theapp/Threads/PurchaseThread:btnEnabler	Lcom/itayc14/theapp/purchaseStuff/PurchaseActivity$buyBtnEnabler;
        //   333: invokeinterface 249 1 0
        //   338: aload_0
        //   339: getfield 41	com/itayc14/theapp/Threads/PurchaseThread:handler	Landroid/os/Handler;
        //   342: new 251	com/itayc14/theapp/Threads/PurchaseThread$1
        //   345: dup
        //   346: aload_0
        //   347: aload 17
        //   349: invokespecial 254	com/itayc14/theapp/Threads/PurchaseThread$1:<init>	(Lcom/itayc14/theapp/Threads/PurchaseThread;Ljava/util/Map;)V
        //   352: invokevirtual 258	android/os/Handler:post	(Ljava/lang/Runnable;)Z
        //   355: pop
        //   356: goto -54 -> 302
        //   359: astore 5
        //   361: aload 4
        //   363: ifnull +8 -> 371
        //   366: aload 4
        //   368: invokevirtual 222	java/io/InputStream:close	()V
        //   371: aload_3
        //   372: ifnull +7 -> 379
        //   375: aload_3
        //   376: invokevirtual 223	java/io/OutputStream:close	()V
        //   379: aload_2
        //   380: ifnull +7 -> 387
        //   383: aload_2
        //   384: invokevirtual 226	javax/net/ssl/HttpsURLConnection:disconnect	()V
        //   387: aload 5
        //   389: athrow
        //   390: astore 21
        //   392: aload 21
        //   394: invokevirtual 214	java/io/IOException:printStackTrace	()V
        //   397: goto -85 -> 312
        //   400: astore 20
        //   402: aload 20
        //   404: invokevirtual 214	java/io/IOException:printStackTrace	()V
        //   407: goto -87 -> 320
        //   410: astore 11
        //   412: aload 11
        //   414: invokevirtual 214	java/io/IOException:printStackTrace	()V
        //   417: goto -203 -> 214
        //   420: astore 10
        //   422: aload 10
        //   424: invokevirtual 214	java/io/IOException:printStackTrace	()V
        //   427: goto -205 -> 222
        //   430: astore 7
        //   432: aload 7
        //   434: invokevirtual 214	java/io/IOException:printStackTrace	()V
        //   437: goto -66 -> 371
        //   440: astore 6
        //   442: aload 6
        //   444: invokevirtual 214	java/io/IOException:printStackTrace	()V
        //   447: goto -68 -> 379
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	450	0	this	PurchaseThread
        //   3	272	1	localLockSeatsForFewMinThread	LockSeatsForFewMinThread
        //   5	379	2	localHttpsURLConnection	javax.net.ssl.HttpsURLConnection
        //   7	369	3	localOutputStream	java.io.OutputStream
        //   9	358	4	localInputStream	java.io.InputStream
        //   359	29	5	localObject	Object
        //   440	3	6	localIOException1	java.io.IOException
        //   430	3	7	localIOException2	java.io.IOException
        //   171	22	8	localIOException3	java.io.IOException
        //   420	3	10	localIOException4	java.io.IOException
        //   410	3	11	localIOException5	java.io.IOException
        //   125	32	13	arrayOfByte	byte[]
        //   134	99	14	localStringBuilder	StringBuilder
        //   143	17	15	i	int
        //   240	108	17	localMap	Map
        //   400	3	20	localIOException6	java.io.IOException
        //   390	3	21	localIOException7	java.io.IOException
        // Exception table:
        //   from	to	target	type
        //   11	136	171	java/io/IOException
        //   136	145	171	java/io/IOException
        //   150	168	171	java/io/IOException
        //   231	302	171	java/io/IOException
        //   329	356	171	java/io/IOException
        //   11	136	359	finally
        //   136	145	359	finally
        //   150	168	359	finally
        //   173	204	359	finally
        //   231	302	359	finally
        //   329	356	359	finally
        //   307	312	390	java/io/IOException
        //   316	320	400	java/io/IOException
        //   209	214	410	java/io/IOException
        //   218	222	420	java/io/IOException
        //   366	371	430	java/io/IOException
        //   375	379	440	java/io/IOException
    }
}



/* Location:           C:\Users\איתי\Desktop\folder\dex\classes_dex2jar.jar

 * Qualified Name:     com.itayc14.theapp.Threads.PurchaseThread

 * JD-Core Version:    0.7.0.1

 */