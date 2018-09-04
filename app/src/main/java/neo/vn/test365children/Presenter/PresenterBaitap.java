package neo.vn.test365children.Presenter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import neo.vn.test365children.ApiService.ApiServiceIml;
import neo.vn.test365children.Listener.CallbackData;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.ErrorApi;

public class PresenterBaitap implements ImpBaitap.Presenter {
    private static final String TAG = "PresenterBaitap";
    ApiServiceIml mApiService;
    ImpBaitap.View mView;

    public PresenterBaitap(ImpBaitap.View mView) {
        this.mView = mView;
        mApiService = new ApiServiceIml();
    }

    @Override
    public void get_api_list_buy(String sUserMe, String sUserCon, String sIdMon, String sIdKhoi) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "get_children");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "1");
        mMap.put("P1", sUserMe);

        mApiService.getApiService(new CallbackData<String>() {
            JSONObject jobj;
            JSONArray jArray;

            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    // List<Childrens> mLis = Childrens.getList(objT);
                    mView.show_error_api(new ArrayList<ErrorApi>());
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);
    }

    @Override
    public void get_api_get_part(String sUserMe, String sUserCon, String sIdDebai) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "get_part");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "3");
        mMap.put("P1", sUserMe);
        mMap.put("P2", sUserCon);
        mMap.put("P3", sIdDebai);

        mApiService.getApiService(new CallbackData<String>() {

            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    List<Cauhoi> mLis = Cauhoi.getList(objT);
                    mView.show_list_get_part(mLis);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);
      /*  String objJson = "[{\"ERROR\":\"0000\",\"INFO\":[{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"285\",\"PART_ID\":\"130\",\"QUESTION\":\"Tổng số nho và dâu trong ảnh là bao nhiêu?\",\"A\":\"5 quả\",\"B\":\"6 quả\",\"C\":\"7 quả\",\"D\":\"8 quả\",\"ANSWER\":\"D\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:46:59.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"284\",\"PART_ID\":\"130\",\"QUESTION\":\"Trong ảnh có mấy quả dâu?\",\"A\":\"3 quả dâu\",\"B\":\"4 quả dâu\",\"C\":\"5 quả dâu\",\"D\":\"6 quả dâu\",\"ANSWER\":\"C\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:46:26.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"283\",\"PART_ID\":\"130\",\"QUESTION\":\"Trong ảnh có mấy quả nho?\",\"A\":\"3 quả nho\",\"B\":\"4 quả nho\",\"C\":\"5 quả nho\",\"D\":\"6 quả nho\",\"ANSWER\":\"A\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:45:34.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"}],\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Lấy ý thành công\",\"ID\":\"130\",\"EXCERCISE_ID\":\"84\",\"QUESTION_NUMBER\":\"8\",\"KIEU\":\"8\",\"HUONGDAN\":\"xem tranh và trả lời câu hỏi\",\"TEXT\":\"\",\"IMAGE_ID\":\"239\",\"PATH_IMAGE\":\"/upload///image/toan//3nho_5dau.png\",\"AUDIO_ID\":\"0\",\"PATH_AUDIO\":\"\",\"UPDATETIME\":\"2018-08-21 16:08:27.0\"},{\"ERROR\":\"0000\",\"INFO\":[{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"264\",\"PART_ID\":\"123\",\"QUESTION\":\"Phân số 5// 25 được viết thành phân số thập phân có mẫu số 100 là:\",\"A\":\"20//50\",\"B\":\"100//250\",\"C\":\"400//1000\",\"D\":\"20//100\",\"ANSWER\":\"D\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:15:07.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"263\",\"PART_ID\":\"123\",\"QUESTION\":\"Phân số nào sau đây là phân số thập phân?\",\"A\":\"6//15                                   <strong> </strong>                   \",\"B\":\"9//20    \",\"C\":\"6//100\",\"D\":\"40/50\",\"ANSWER\":\"C\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:14:21.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"266\",\"PART_ID\":\"123\",\"QUESTION\":\"Tính: 6//10 – 1//10 = ?<br /><br> \",\"A\":\"6//7\",\"B\":\"30//16\",\"C\":\"1//2\",\"D\":\"6//8\",\"ANSWER\":\"C\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:16:02.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"267\",\"PART_ID\":\"123\",\"QUESTION\":\"Tính: 3 + 2//3 = ?<br /><br> \",\"A\":\"11//3\",\"B\":\"5//3   \",\"C\":\"7//3  \",\"D\":\"1\",\"ANSWER\":\"A\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:16:41.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"}],\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Lấy ý thành công\",\"ID\":\"123\",\"EXCERCISE_ID\":\"84\",\"QUESTION_NUMBER\":\"1\",\"KIEU\":\"1\",\"HUONGDAN\":\"chọn đáp án đúng nhất\",\"TEXT\":\"\",\"IMAGE_ID\":\"0\",\"PATH_IMAGE\":\"\",\"AUDIO_ID\":\"0\",\"PATH_AUDIO\":\"\",\"UPDATETIME\":\"2018-08-21 16:03:24.0\"},{\"ERROR\":\"0000\",\"INFO\":[{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"282\",\"PART_ID\":\"129\",\"QUESTION\":\"Hà thích làm điều gì bên cửa sổ?<br /><br> \",\"A\":\"Ngắm nhìn bầu trời không chán.\",\"B\":\"Ngửi hương thơm của cây trái.\",\"C\":\"Nhổ tóc sâu cho bà, nghe bà kể chuyện cổ tích.\",\"D\":\"Đọc sách và nghe nhạc\",\"ANSWER\":\"C\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-27 14:12:16.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"281\",\"PART_ID\":\"129\",\"QUESTION\":\"Trăng được so sánh với những gì?<br /><br> \",\"A\":\"Chiếc thuyền vàng\",\"B\":\"Hoa lá trên mặt bàn\",\"C\":\"Chiếc đèn lồng\",\"D\":\"Đáp án A và C đều đúng\",\"ANSWER\":\"A\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-27 14:12:02.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"280\",\"PART_ID\":\"129\",\"QUESTION\":\"Trong câu Những ngọn bạch đàn chanh coa vút ấy bỗng chốc đâm những # búp vàng:, từ búp vàng chỉ gì?<br /><br> \",\"A\":\"Cây bạch đàn\",\"B\":\"Đàng vàng anh\",\"C\":\"Ngọn bạch đàn\",\"D\":\"Lá bạch đàn\",\"ANSWER\":\"C\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:39:54.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"278\",\"PART_ID\":\"129\",\"QUESTION\":\"Bầu trời bên ngoài cửa sổ của bé Hà như thế nào?<br /><br> \",\"A\":\"Chói chang\",\"B\":\"<strong>Thật đẹp</strong>\",\"C\":\"U ám\",\"D\":\"Đen tối\",\"ANSWER\":\"B\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:38:27.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"279\",\"PART_ID\":\"129\",\"QUESTION\":\"Qua khung cửa sổ nhà mình, Hà cảm nhận được những hình ảnh. Âm thanh nào?<br /><br> \",\"A\":\"Bầu trời đầy ánh sáng, đầy màu sắc, đàn vàng anh sắc lông óng ánh như dát vàng, tiếng chim hót như những chuỗi vàng lọc nắng, ánh nắng dịu dàng, ngọt màu mật ong.\",\"B\":\"Bầu trời u ám, tiếng những giọt mưa thánh thót, những cành cây vật vã trong gió.\",\"C\":\"Bầu trời đang đầy ánh nắng thì mây đen kéo đến gây mưa.\",\"D\":\"Nắng như đổ lửa, trâu nằm lim dim dưới bụi tre già, ve kêu inh ỏi.\",\"ANSWER\":\"A\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:39:15.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"}],\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Lấy ý thành công\",\"ID\":\"129\",\"EXCERCISE_ID\":\"84\",\"QUESTION_NUMBER\":\"7\",\"KIEU\":\"7\",\"HUONGDAN\":\"đọc đoạn văn và trả lời câu hỏi \",\"TEXT\":\"BẦU TRỜI NGOÀI CỬA SỔ<br>Đó là khung cửa sổ có bầu trời bên ngoài thật đẹp. Bầu trời ngoài cửa sổ ấy, lúc thì như một bức tranh nhiều màu sắc, lúc thì như một trang sách hay. Bầu trời bên ngoài cửa sổ, Hà chỉ nhìn sắc mây thôi cũng có thể đoán biết mưa hay nắng, dông bão hay yên lành.<br>Bầu trời ngoài cửa sổ của bé Hà thường đầy ánh sáng, đầy màu sắc. Ở đấy, Hà thấy bao nhiêu điều lạ. Một đàn vàng anh, vàng như dát vàng lên lông, lên cánh ấy, mà con trống bao giờ cũng to hơn, óng ánh sắc lông hơn – chợt bay đến rồi bay đi. Nhưng có lúc, đàn vàng anh ấy đậu trên ngọn chót vót những cây bạch đàn chanh cao nhất giữa bầu trời ngoài cửa sổ. Những ngọn bạch đàn chanh cao vút ấy bỗng chốc đâm những #búp vàng#. Rồi từ trên chót vót cao, vàng anh trống cất tiếng hót. Tiếng hót mang theo hương thơm lá bạch đàn chanh từ bầu trời bay vào cửa sổ. Đàn chim chớp cánh vàng khoe sắc với nắng rực rỡ, và tiếng chim lại như những chuỗi vàng lọc nắng bay đến với Hà. Chốc sau đàn chim chao cánh bay đi, nhưng tiếng hót như đọng mãi giữa bầu trời ngoài cửa sổ.<br>Buổi sáng, ánh nắng dịu dàng, ngọt màu mật ong từ bầu trời ngoài cửa sổ rọi vào nhà, in hình hoa lá trên mặt bàn, nền gạch hoa. Còn về đêm, trăng khi thì như chiếc thuyền vàng trôi trong mây trên bầu trời ngoài cửa sổ, lúc thì như chiếc đèn lồng thả ánh sáng xuống đầy sân.<br>Ôi, khung cửa sổ nhỏ! Hà yêu nó quá! Hà thích ngồi bên cửa sổ nhổ tóc sâu cho bà, nghe bà kể <br>\",\"IMAGE_ID\":\"0\",\"PATH_IMAGE\":\"\",\"AUDIO_ID\":\"0\",\"PATH_AUDIO\":\"\",\"UPDATETIME\":\"2018-08-21 16:07:08.0\"},{\"ERROR\":\"0000\",\"INFO\":[{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"291\",\"PART_ID\":\"132\",\"QUESTION\":\"<strong>Hành tinh mà chúng ta đang sống được gọi là gì?</strong><br /><br> \",\"A\":\"Mặt trăng\",\"B\":\"Mặt trời\",\"C\":\"Sao Hỏa\",\"D\":\"Trái Đất\",\"ANSWER\":\"D\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 17:06:19.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"290\",\"PART_ID\":\"132\",\"QUESTION\":\"<strong>Bài hát Quê hương tươi đẹp là dân ca của dân tộc nào?</strong><br /><br> \",\"A\":\"Thái\",\"B\":\"Tày\",\"C\":\"Nùng\",\"D\":\"Dao\",\"ANSWER\":\"C\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 17:04:40.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"289\",\"PART_ID\":\"132\",\"QUESTION\":\"<strong>Bộ phận nào không có trên cơ thể người? </strong><br /><br> \",\"A\":\"Chân \",\"B\":\"Tay \",\"C\":\"Đầu\",\"D\":\"Đuôi\",\"ANSWER\":\"D\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 17:03:52.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"292\",\"PART_ID\":\"132\",\"QUESTION\":\"<strong>Hành tinh mà chúng ta đang sống được gọi là gì?</strong><br /><br> \",\"A\":\"Mặt trăng\",\"B\":\"Mặt trời\",\"C\":\"Sao Hỏa\",\"D\":\"Trái Đất\",\"ANSWER\":\"D\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 17:06:20.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"293\",\"PART_ID\":\"132\",\"QUESTION\":\"<strong>Mẹ đi vắng, em muốn giúp mẹ cất gọn quần áo từ dây phơi vào trong tủ. Công việc này cần thực hiện theo các bước nào?</strong><br /><br> \",\"A\":\"Rút quần áo từ dây phơi, cất quần áo vào tủ, gấp quần áo\",\"B\":\"Rút quần áo từ dây phơi, gấp quần áo, cho quần áo vào máy giặt\",\"C\":\"Gấp quần áo, cất quần áo vào tủ, treo quần áo trên dây phơi\",\"D\":\"Rút quần áo từ dây phơi, gấp quần áo, cất quần áo vào tủ\",\"ANSWER\":\"D\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 17:09:27.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"}],\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Lấy ý thành công\",\"ID\":\"132\",\"EXCERCISE_ID\":\"84\",\"QUESTION_NUMBER\":\"10\",\"KIEU\":\"10\",\"HUONGDAN\":\"chọn đáp án đúng để tiến gần tới ô cửa giải cứu công chúa\",\"TEXT\":\"\",\"IMAGE_ID\":\"0\",\"PATH_IMAGE\":\"\",\"AUDIO_ID\":\"0\",\"PATH_AUDIO\":\"\",\"UPDATETIME\":\"2018-08-21 16:10:08.0\"},{\"ERROR\":\"0000\",\"INFO\":[{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"286\",\"PART_ID\":\"131\",\"QUESTION\":\"Phân số 17//8 bằng hỗn số nào dưới đây?<br /><br> \",\"A\":\"2||5//8    \",\"B\":\"2||1//8     \",\"C\":\"2||7//8 \",\"D\":\"2||3/8\",\"ANSWER\":\"B\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:50:27.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"288\",\"PART_ID\":\"131\",\"QUESTION\":\"Tính: 3||1//3 + 4||3//5 = ?<br /><br> \",\"A\":\"7||5//15   \",\"B\":\"7||5//15\",\"C\":\"7||11//15\",\"D\":\"7||14//15\",\"ANSWER\":\"D\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 17:02:42.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"287\",\"PART_ID\":\"131\",\"QUESTION\":\"Tính: 3||1//3 + 4||3//5 = ?<br /><br> \",\"A\":\"7||5//15   \",\"B\":\"7||5//15\",\"C\":\"7||11//15\",\"D\":\"7||14//15\",\"ANSWER\":\"D\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 17:02:39.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"}],\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Lấy ý thành công\",\"ID\":\"131\",\"EXCERCISE_ID\":\"84\",\"QUESTION_NUMBER\":\"9\",\"KIEU\":\"9\",\"HUONGDAN\":\"nghe và trả lời câu hỏi bên dưới\",\"TEXT\":\"\",\"IMAGE_ID\":\"0\",\"PATH_IMAGE\":\"\",\"AUDIO_ID\":\"12\",\"PATH_AUDIO\":\"/upload///audio/listen//G2_1A_Listen.mp3\",\"UPDATETIME\":\"2018-08-21 16:09:17.0\"},{\"ERROR\":\"0000\",\"INFO\":[{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"43\",\"PART_ID\":\"128\",\"QUESTION\":\"l hay n?<br /><br>a,  <<l>>ao động. <br /><br>b, chắc <<n>>ịch. <br /><br>c, tờ <<l>>ịch. <br /><br>d, <<n>>ông dân. \",\"POINT\":\"2\",\"UPDATETIME\":\"2018-08-21 16:37:31.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"41\",\"PART_ID\":\"128\",\"QUESTION\":\"c hay k?<br /><br>a,  kiên <<c>>ường. <br /><br>b, <<c>>ủng cố. <br /><br>c,  <<k>>én chọn. <br /><br>d, con <<k>>iến. \",\"POINT\":\"2\",\"UPDATETIME\":\"2018-08-21 16:34:31.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"}],\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Lấy ý thành công\",\"ID\":\"128\",\"EXCERCISE_ID\":\"84\",\"QUESTION_NUMBER\":\"6\",\"KIEU\":\"6\",\"HUONGDAN\":\"Điền vào ô trống (…)  \",\"TEXT\":\"null\",\"IMAGE_ID\":\"0\",\"PATH_IMAGE\":\"\",\"AUDIO_ID\":\"0\",\"PATH_AUDIO\":\"\",\"UPDATETIME\":\"2018-08-21 16:33:36.0\"},{\"ERROR\":\"0000\",\"INFO\":[{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"22\",\"PART_ID\":\"126\",\"QUESTION\":\"Sắp xếp theo thứ từ bé đến lớn:<br /><br>23 :: 69 :: 45 + 34 :: 88 :: 91\",\"POINT\":\"2\",\"UPDATETIME\":\"2018-08-21 16:29:51.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"21\",\"PART_ID\":\"126\",\"QUESTION\":\"Sắp sếp theo thứ tự từ lớn đến bé :<br /><br>75 :: 57 :: 53 :: 12 + 23 :: 27\",\"POINT\":\"2\",\"UPDATETIME\":\"2018-08-21 16:28:47.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"}],\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Lấy ý thành công\",\"ID\":\"126\",\"EXCERCISE_ID\":\"84\",\"QUESTION_NUMBER\":\"4\",\"KIEU\":\"4\",\"HUONGDAN\":\"sắp xếp theo yêu cầu\",\"TEXT\":\"null\",\"IMAGE_ID\":\"0\",\"PATH_IMAGE\":\"\",\"AUDIO_ID\":\"0\",\"PATH_AUDIO\":\"\",\"UPDATETIME\":\"2018-08-28 09:01:27.0\"},{\"ERROR\":\"0000\",\"INFO\":[{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"21\",\"PART_ID\":\"127\",\"EGG_1\":\"trứng 1::rổ 1\",\"EGG_2\":\"trứng 2::rổ 2\",\"EGG_3\":\"trứng 3::rổ 3\",\"EGG_4\":\"trứng 4::rổ 4\",\"POINT\":\"2\",\"UPDATETIME\":\"2018-08-24 08:59:32.0\",\"EGG_1_RESULT\":\"\",\"EGG_2_RESULT\":\"\",\"EGG_3_RESULT\":\"\",\"EGG_4_RESULT\":\"\",\"POINT_CHILD\":\"\"}],\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Lấy ý thành công\",\"ID\":\"127\",\"EXCERCISE_ID\":\"84\",\"QUESTION_NUMBER\":\"5\",\"KIEU\":\"5\",\"HUONGDAN\":\"xếp trứng vào rổ thích hợp\",\"TEXT\":\"\",\"IMAGE_ID\":\"0\",\"PATH_IMAGE\":\"\",\"AUDIO_ID\":\"0\",\"PATH_AUDIO\":\"\",\"UPDATETIME\":\"2018-08-21 16:05:39.0\"},{\"ERROR\":\"0000\",\"INFO\":[{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"275\",\"PART_ID\":\"125\",\"QUESTION\":\"2 + 2//3 x 7//8 =?<br /><br> \",\"A\":\"10//9 \",\"B\":\"22//9\",\"C\":\"31//12\",\"D\":\"8//3\",\"ANSWER\":\"C\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:24:21.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"273\",\"PART_ID\":\"125\",\"QUESTION\":\"5//8 x 2 – 2//5 : 8//15 = ?<br /><br> \",\"A\":\"5//4\",\"B\":\"1//2\",\"C\":\"3//4  \",\"D\":\"1\",\"ANSWER\":\"\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:25:43.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"276\",\"PART_ID\":\"125\",\"QUESTION\":\"4//5 :3//4 x 4//7 = ?<br /><br> \",\"A\":\"48//140    \",\"B\":\"16//15    \",\"C\":\"48//105   \",\"D\":\"64//105\",\"ANSWER\":\"D\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:25:03.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"272\",\"PART_ID\":\"125\",\"QUESTION\":\"1 – (1//3 + 1//2) =?<br /><br> \",\"A\":\"1//6   \",\"B\":\"5//6  \",\"C\":\"3//2\",\"D\":\"2//3\",\"ANSWER\":\"\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:25:27.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"}],\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Lấy ý thành công\",\"ID\":\"125\",\"EXCERCISE_ID\":\"84\",\"QUESTION_NUMBER\":\"3\",\"KIEU\":\"3\",\"HUONGDAN\":\"chọn đáp án đúng nhất\",\"TEXT\":\"\",\"IMAGE_ID\":\"0\",\"PATH_IMAGE\":\"\",\"AUDIO_ID\":\"0\",\"PATH_AUDIO\":\"\",\"UPDATETIME\":\"2018-08-21 16:04:39.0\"},{\"ERROR\":\"0000\",\"INFO\":[{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"271\",\"PART_ID\":\"124\",\"QUESTION\":\"Tìm a: sao cho: 1//5 x a = 2//3 – 4//6\",\"A\":\"a = 0    \",\"B\":\"a = 2//3\",\"C\":\"a = 4//6 \",\"D\":\"a = 1//5\",\"ANSWER\":\"A\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:20:35.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"269\",\"PART_ID\":\"124\",\"QUESTION\":\"7//8 : 5//6 = ?\",\"A\":\"21//20   \",\"B\":\"4//20   \",\"C\":\"1//2  \",\"D\":\"3//2\",\"ANSWER\":\"A\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:18:58.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"270\",\"PART_ID\":\"124\",\"QUESTION\":\"Tìm a, sao cho: a : 4//5 = 5//7 : 4//5\",\"A\":\"a = 5//6   \",\"B\":\"a = 5//7  \",\"C\":\"a = 5//7\",\"D\":\"a = 4//9\",\"ANSWER\":\"B\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:19:39.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Mô tả đề thành công\",\"ID\":\"268\",\"PART_ID\":\"124\",\"QUESTION\":\"4//7 x 1//2 =\",\"A\":\"14//4\",\"B\":\"2//7  \",\"C\":\"8//7  \",\"D\":\"5//14\",\"ANSWER\":\"B\",\"EXPLAIN\":\"\",\"UPDATETIME\":\"2018-08-21 16:17:50.0\",\"ANSWER_CHILD\":\"\",\"RESULT_CHILD\":\"\",\"POINT_CHILD\":\"\"}],\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Lấy ý thành công\",\"ID\":\"124\",\"EXCERCISE_ID\":\"84\",\"QUESTION_NUMBER\":\"2\",\"KIEU\":\"2\",\"HUONGDAN\":\"chọn đáp án đúng nhất\",\"TEXT\":\"\",\"IMAGE_ID\":\"0\",\"PATH_IMAGE\":\"\",\"AUDIO_ID\":\"0\",\"PATH_AUDIO\":\"\",\"UPDATETIME\":\"2018-08-21 16:03:39.0\"}]";
        try {
            //jArray = new JSONArray(c);
            List<Cauhoi> mLis = Cauhoi.getList(objJson);
            mView.show_list_get_part(mLis);
        } catch (Exception e) {
            e.printStackTrace();
            mView.show_error_api(null);
            Log.i(TAG, "Log_error_api_filght: " + e);
        }*/
    }

    @Override
    public void get_api_get_excercise_needed(String sUserMe, String sUserCon, String sDay) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "get_excercise_needed");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "3");
        mMap.put("P1", sUserMe);
        mMap.put("P2", sUserCon);
        mMap.put("P3", sDay);

        mApiService.getApiService(new CallbackData<String>() {

            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    List<Baitap_Tuan> mLis = Baitap_Tuan.getList(objT);
                    mView.show_get_excercise_needed(mLis);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);
    }

    @Override
    public void get_api_get_excercise_expired(String sUserMe, String sUserCon) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "get_excercise_expired");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "2");
        mMap.put("P1", sUserMe);
        mMap.put("P2", sUserCon);

        mApiService.getApiService(new CallbackData<String>() {

            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    List<Baitap_Tuan> mLis = Baitap_Tuan.getList(objT);
                    mView.show_get_excercise_expired(mLis);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);
    }
}
