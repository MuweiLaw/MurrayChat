import com.murray.utils.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;

public class TestIFrame extends JFrame {
    private JLabel time;
    SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public TestIFrame() {
        super();
        setLayout(new FlowLayout());
        setUndecorated(true);
        setLocationRelativeTo(null);
        setSize(400,40);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        time = new JLabel(System.currentTimeMillis()+"");
        time.setFont(FontUtil.MICROSOFT_YA_HEI_B20);
        this.add(time);
        setVisible(true);
        while (true){
            time.setText(myfmt.format(new java.util.Date()));
        }
    }


    public void actionPerformed(ActionEvent ae) {
        time.setText(myfmt.format(new java.util.Date()).toString());
    }

    public static void main(String[] args) {
        new TestIFrame();
    }

}