package com.murray.dto.response;

import java.util.Map;

/**
 * @author Murray Law
 * @describe 群聊基本信息的响应头
 * @createTime 2020/11/10
 */
public class GroupBasic {
    /**
     * 群编号
     */
    private String groupNo;

    /**
     * 群名称
     */
    private String groupName;
    /**
     * 最后发言内容
     */
    private String lastSpeakingMsg;

    private Map<String, Byte> userRoleMap;

    public GroupBasic() {
    }

    public GroupBasic(String groupNo, String groupName, String lastSpeakingMsg, Map<String, Byte> userRoleMap) {
        this.groupNo = groupNo;
        this.groupName = groupName;
        this.lastSpeakingMsg = lastSpeakingMsg;
        this.userRoleMap = userRoleMap;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public String getLastSpeakingMsg() {
        return lastSpeakingMsg;
    }

    public void setLastSpeakingMsg(String lastSpeakingMsg) {
        this.lastSpeakingMsg = lastSpeakingMsg;
    }

    public Map<String, Byte> getUserRoleMap() {
        return userRoleMap;
    }

    public void setUserRoleMap(Map<String, Byte> userRoleMap) {
        this.userRoleMap = userRoleMap;
    }

    @Override
    public String toString() {
        return "GroupBasic{" +
                "groupNo='" + groupNo + '\'' +
                ", groupName='" + groupName + '\'' +
                ", lastSpeakingMsg='" + lastSpeakingMsg + '\'' +
                ", userRoleMap=" + userRoleMap +
                '}';
    }
}
