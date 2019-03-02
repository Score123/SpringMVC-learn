package com.bee.util.encryptutil;

/**
 * Created by AndroidXJ on 2018/5/29.
 */

public class KeyUtil {
    //服务端的RSA公钥(Base64编码)
    public final static String SERVER_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuyxjszdOFDpplSoRgqmP5atYWtbraAxC" +
            "fYzOFu6ZcsgxGOKy40i46dxelqutDnzzwLNj5jHEDLareEpD7GigC2kduLQIQGeM4JTo2T2D/oVg" +
            "FMdzPVYvrcScfaMwvKkYfI7d/QZjuxwg+b/g4XlsKOcp1iK0JrfToJsPui46RUAZzmJ8ckJ95Aj7" +
            "uATsDIurUHHzsqlynRhzI3ON40mE2Qd8b9ME57w1Xf6/xXcIaeL6Y4F7PoAMrcdUQM/9JNiik3hI" +
            "zZ9iX+uPjLPejiORqN/mHBnJO0LqIdrkckfWXxmvBOF0rQ+FJdt4QsTPU/D9sX3OnUOl5qZgjizx" +
            "aZqmCwIDAQAB";

    //服务端的RSA私钥(Base64编码)
    public final static String SERVER_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC7LGOzN04UOmmVKhGCqY/lq1ha" +
            "1utoDEJ9jM4W7plyyDEY4rLjSLjp3F6Wq60OfPPAs2PmMcQMtqt4SkPsaKALaR24tAhAZ4zglOjZ" +
            "PYP+hWAUx3M9Vi+txJx9ozC8qRh8jt39BmO7HCD5v+DheWwo5ynWIrQmt9Ogmw+6LjpFQBnOYnxy" +
            "Qn3kCPu4BOwMi6tQcfOyqXKdGHMjc43jSYTZB3xv0wTnvDVd/r/Fdwhp4vpjgXs+gAytx1RAz/0k" +
            "2KKTeEjNn2Jf64+Ms96OI5Go3+YcGck7Quoh2uRyR9ZfGa8E4XStD4Ul23hCxM9T8P2xfc6dQ6Xm" +
            "pmCOLPFpmqYLAgMBAAECggEBAJTpX/i5S1k0j8Oo0H2PwXgLtiOvUWJaPpv8ZgpAkbyMIOOXmJLS" +
            "zKriNC7TLr+UEamCDzyY40zqF8l/7EItiwcI/ihwsw/qUl5qmkYbduQL4MEnXTuzUgOBpK8gIio2" +
            "rdCLKXmgl7/JRULtsVAxdiekodhHi+Qx5CCmvQB2vs1QMAi4qd/JmoK5x8znxxx8rCR7ScxLP+Dr" +
            "syCzoSFL0FOWMdlIRa6028+W7BPip+JQVgVoxN3A6eCTIhKqUPZpkiAdOmoncRSO6fdiJ5FX46Di" +
            "U6w3FTePtNTplNye3mQ9YOwNyQrQrI2ciBS081xMEi0FPoAvRjGxkp9Wgtux0GECgYEA8tP7ivd1" +
            "PvRX2xyji8Ci5/yERAussBr0ynJAjaawA/Y0j450t8xOqxRDYI1cEjWJi1i17wIuIW7qROPOjRRW" +
            "EAin9aiT3oYldmprPcI04eWdYouzroVs3dPmotamOaB7N2HD8uRspLJ4/sCcD1UFIvp9NbCT/mRh" +
            "bcKXoZRAO20CgYEAxVOPzNmVjoiGSAediBq2qOMzbyiQLCrsMmvq2zY7WBQHKL5wZOpHDd65Dl6V" +
            "zrl23yKuvsZvdVzE+BTtTyUtjPnbuQ6HcZD9eA9Fqu7anDzCZzY9hbFkoN+vvsdjp5wOA3uHTJji" +
            "ZM8sjlwDq5+iMcBfiWcC7vjVbyXNHcgDxFcCgYEA7I4d6k8PZPjb7ZSErkxHZv/USgWlgRmBhwau" +
            "ZFASAIoEwiThB3x81qSyzohnPUXybFFLSz8weVl31wyl10IMZSpJlUNYadr8+9LIDi33Hml1XN8D" +
            "d+x7Fc6sS84XuOPhpR4UqbZlMDy9Kk+wuC73BhBHkdonEhfv4eFDniCyQjECgYEAsZrbhEqXBPo2" +
            "uqA4FufMXkhY5AUDdu2POHbPpUyzrzwfkXmPGhWPrJk5wQg7GBzwNDg4Rje+3EiKdKwJSEIzNH8z" +
            "V+YO3JXevfApCYgAtb0uUM2OJEYdR3rt6O338UVRi6rkhXnZ01hxRxTkrNV6nAMFOlvAVS0clfTV" +
            "IKgFU/MCgYAmH93Hf1b213abuWKMynbuOjIA8pJPu3iWC+4E9UB1I5i+wjCOvLedacPvJSGlwCjX" +
            "+yE/u4P2uPdys5+UcS73rwS1dVq7LbIk4NbzCoi/efz4Z1GI9gW4KdCCIV7+geRKMjaarAle+W92" +
            "1/bSIInSsEearqPJhpedhDBzwNEyfA==";


    /**
     * 创建指定数量的随机字符串
     * @param numberFlag 是否是数字
     * @param length
     * @return
     */
    public static String createRandom(boolean numberFlag, int length){
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }
}
