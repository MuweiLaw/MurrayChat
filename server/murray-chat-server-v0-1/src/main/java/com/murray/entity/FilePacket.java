package com.murray.entity;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;

import static com.murray.config.ServerCache.SERVER_FILE_SAVE_PATH;

@Component
public class FilePacket extends Packet implements Serializable {
    private static final long serialVersionUID = 4833753986829270788L;
    private File file;
    private String fileNo;
    private String fileName;

    public FilePacket(String fileNo,String fileName) {
        this.file = new File(SERVER_FILE_SAVE_PATH,fileName);
        this.fileNo = fileNo;
        this.fileName = fileName;

    }
    public FilePacket() {
    }
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Byte getCommand() {
        return Command.FILE;
    }


}
