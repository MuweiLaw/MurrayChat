import com.murray.dto.response.ChatUserInfoResponse;
import com.murray.dto.response.GroupBasic;
import com.murray.view.vo.frame.GroupInfoFrame;

import java.util.HashMap;

import static com.murray.cache.ClientCache.CHAT_USER_INFO_RESPONSE_MAP;
import static com.murray.cache.ClientCache.GROUP_BASIC_MAP;

/**
 * @author Murray Law
 * @describe 测试群聊信息窗口
 * @createTime 2020/12/24
 */
public class TestGroupFrame {
    public static void main(String[] args) {
        /**************/
        HashMap<String, Byte> stringByteHashMap = new HashMap<>();
        stringByteHashMap.put("2562758836", (byte) 0);
        stringByteHashMap.put("123456", (byte) 1);
        stringByteHashMap.put("a", (byte) 1);
        GROUP_BASIC_MAP.put("a", new GroupBasic("a", "这是一个群聊", "搜索说", stringByteHashMap));

        CHAT_USER_INFO_RESPONSE_MAP.put("2562758836",new ChatUserInfoResponse("2562758836","罗木伟",null,null,null,null,null,"码农",null,null,null));
        CHAT_USER_INFO_RESPONSE_MAP.put("123456",new ChatUserInfoResponse("123456","罗木伟1",null,null,null,null,null,"码农1",null,null,null));
        CHAT_USER_INFO_RESPONSE_MAP.put("a",new ChatUserInfoResponse("a","罗木伟2",null,null,null,null,null,"码农2",null,null,null));
        /**********/
        new GroupInfoFrame("a",null);
    }

}
