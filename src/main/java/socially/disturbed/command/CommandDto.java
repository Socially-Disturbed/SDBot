package socially.disturbed.command;

import discord4j.core.object.entity.Message;

public class CommandDto {
    private Message message;
    private boolean deleteCommandMsg = false;

    private boolean deleteLastChannelMsg = false;
    private String returningMsg;

    private String returnMsgChannelId;

    public CommandDto(Message message) {
        this.message = message;
    }

    public String getReturningMsg() {
        return returningMsg;
    }

    public void setReturningMsg(String returningMsg) {
        this.returningMsg = returningMsg;
    }

    public String getMethodName() {
        String msg = message.getContent();
        return msg.contains(" ") ?
                msg.substring(1, msg.indexOf(" ")) :
                msg.substring(1);
    }

    public String getCommandArguments() {
        return message.getContent().substring(message.getContent().indexOf(" ") + 1);
    }

    public boolean deleteCommandMsg() {
        return deleteCommandMsg;
    }

    public void deleteCommandMsg(boolean deleteCommandMsg) {
        this.deleteCommandMsg = deleteCommandMsg;
    }

    public boolean deleteLastChannelMsg() {
        return deleteLastChannelMsg;
    }

    public void deleteLastChannelMsg(boolean deleteLastChannelMsg) {
        this.deleteLastChannelMsg = deleteLastChannelMsg;
    }

    public String getReturnMsgChannelId() {
        return returnMsgChannelId;
    }

    public void setReturnMsgChannelId(String returnMsgChannelId) {
        this.returnMsgChannelId = returnMsgChannelId;
    }
}
