package com.murray.entity;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.io.File;
import java.io.Serializable;

public class FilePacket extends Packet implements Serializable {

    private static final long serialVersionUID = 440570812485202515L;
    private File file;
    private String fileNo;
    private String fileName;

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

    public int getFileNameLength() {
        return  this.fileName.getBytes().length;
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
