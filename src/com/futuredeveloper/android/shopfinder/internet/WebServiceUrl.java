package com.futuredeveloper.android.shopfinder.internet;

public class WebServiceUrl {

    public static final String BaseApiAddress       = "http://www.hoojibooji.ir";

    public static final String Person               = BaseApiAddress + "personservice";

    public static final String RegisterAddress      = "http://sf.armannematy.ir/api/store/register";

    public static final String LoginAddress         = BaseApiAddress + "/Token";
    public static final String registerCustomer     = BaseApiAddress + "/api/account/registercustomer";

    public static final String loginLocal           = "http://localhost:5320/token";

    public static final String Regist_Product       = "http://sf.armannematy.ir/api/Product/SetProductModelForInsert?id=1002";

    public static final String MessageAddress       = BaseApiAddress + "/Message/SendMessage?message=";
    public static final String test_user_name       = " http://sf.armannematy.ir/api/store/CheackUserName?username=";
    public static final String getAllProduct        = BaseApiAddress + "/firstPage/GetActiveSliderAndBoxAndAdvertise";
    public static final String getNewStore          = BaseApiAddress + "/api/firstPage/GetNewestStore?";

    public static final String GetmessageAddress    = BaseApiAddress + "/Message/GetMessageByDateAndTime?";
    public static final String GetSlider            = BaseApiAddress + "/api/firstPage/GetActiveSlider";
    public static final String GetNewKala           = BaseApiAddress + "/api/product/advanceSearch";
    public static final String GetShowProduct       = BaseApiAddress + "/api/product/getproduct?";
    public static final String GetShowStore         = BaseApiAddress + "/api/store/get?";

    public static final String GetSearch            = BaseApiAddress + "/api/product/advanceSearch";
    public static final String GetCategory          = BaseApiAddress + "/api/product/getMenue";
    public static final String GetFilterEachProduct = BaseApiAddress + "/api/product/getRequiredItems?";
    public static final String GetNewProgram        = BaseApiAddress + "/api/Application/CheckForPublicMessage";
    public static final String sendContact          = BaseApiAddress + "/api/firstPage/ContactUs";
    public static final String sendNazar            = BaseApiAddress + "/api/Application/UserSurvey";
    public static final String sendReportProduct    = BaseApiAddress + "/api/Application/UserProductReport?";
    public static final String sendReportStore      = BaseApiAddress + "/api/Application/UserStoreReport?";
    public static final String GetUpdate            = BaseApiAddress + "/api/Application/CheckLastApkSize?";

}
