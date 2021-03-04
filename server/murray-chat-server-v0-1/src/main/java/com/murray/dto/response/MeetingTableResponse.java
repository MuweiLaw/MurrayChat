package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.util.List;

/**
 * @author Murray Law
 * @describe 会议表格响应
 * @createTime 2020/11/25
 */
public class MeetingTableResponse extends Packet {
    private List<MeetingTableCell> meetingTableCellList;

    public MeetingTableResponse() {
    }

    public MeetingTableResponse(List<MeetingTableCell> meetingTableCellList) {
        this.meetingTableCellList = meetingTableCellList;
    }

    public List<MeetingTableCell> getMeetingTableCellList() {
        return meetingTableCellList;
    }

    public void setMeetingTableCellList(List<MeetingTableCell> meetingTableCellList) {
        this.meetingTableCellList = meetingTableCellList;
    }
    @Override
    public Byte getCommand() {
        return Command.MEETING_TABLE_RESPONSE;
    }


}
