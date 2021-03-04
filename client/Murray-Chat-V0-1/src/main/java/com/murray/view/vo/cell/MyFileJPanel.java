package com.murray.view.vo.cell;

import com.murray.utils.BorderUtil;
import com.murray.utils.DimensionUtil;
import com.murray.utils.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static com.murray.cache.ClientCache.*;

/**
 * @author Murray Law
 * @describe 我的文件面板
 * @createTime 2020/11/18
 */
public class MyFileJPanel extends JPanel {
    private File file;

    public MyFileJPanel(File file) {
        this.file = file;
        JLabel iconLabel = new JLabel();
        ImageIcon imageIcon = null;
        //获取文件类型
        String fileType=file.getPath().substring(file.getPath().lastIndexOf(".")+1);
        switch (fileType) {
            case "pdf":
            case "PDF":
                imageIcon = PDF_ICON;
                break;
            case "zip":
            case "ZIP":
                imageIcon = ZIP_ICON;
                break;
            case "jpeg":
                break;
            case "JPEG":
                break;
            case "jpg":
                break;
            case "JPG":
                break;
            case "png":
                break;
            case "PNG":
                break;
            case "xls":
            case "xlsx":
                imageIcon = EXCEL_ICON;
                break;
            case "doc":
            case "docx":
                imageIcon = WORD_ICON;
                break;
            case "ppt":
            case "pptx":
                imageIcon = PPT_ICON;
                break;

            default:
                imageIcon=FILE_ICON;
        }

        iconLabel.setIcon(imageIcon);
        iconLabel.setBounds(5, 5, 60, 60);

        JLabel fileName = new JLabel(file.getName());
        fileName.setBounds(70, 0, 200, 40);
        fileName.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        //文件大小紧缺到小数点后两位
        String fileSize;
        double fileKbSize =  file.length() / 1024.00;
        if (fileKbSize<1024.00){
            fileSize= String.format("%.2f",fileKbSize) + "KB";
        }else {
            fileSize= String.format("%.2f",fileKbSize/1024.00) + "MB";
        }
        JLabel fileSizeLabel = new JLabel(fileSize);
        fileSizeLabel.setBounds(70, 40, 200, 20);
        fileSizeLabel.setFont(FontUtil.MICROSOFT_YA_HEI_14);
        fileSizeLabel.setForeground(Color.lightGray);

        setBorder(BorderUtil.BORDER_WHITE_235);
        setLayout(null);
        add(iconLabel);
        add(fileName);
        add(fileSizeLabel);
        setPreferredSize(DimensionUtil.DIM_325_70);
        setMaximumSize(DimensionUtil.DIM_325_70);
        setMinimumSize(DimensionUtil.DIM_325_70);
        setBackground(Color.WHITE);
    }

    public String getFilePath() {
        return file.getAbsolutePath();
    }
}
