package warmGame;

import java.awt.BorderLayout;
//import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame{
    /**
	 *
	 */
	//private static final long serialVersionUID = 1L;
	public String[] PanelNames = {"spanel","lpanel","gpanel"};
    StartPanel spanel = new StartPanel(this,PanelNames[0]);
    LoadPanel lpanel = new LoadPanel(this, PanelNames[1]);
    GamePanel gpanel = new GamePanel(this,PanelNames[2]);

    private JPanel contentPane;
    private JTextField positionField;

    public MainFrame(){
    	this.add(spanel);
    	spanel.setVisible(true);

    	this.add(lpanel);
    	lpanel.setVisible(false);

    	this.add(gpanel);
    	gpanel.setVisible(false);

    	this.setBounds(100, 100, 800, 800);
    }

    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {
    		public void run(){
    			MainFrame mframe = new MainFrame();
    			mframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
    			mframe.setVisible(true);
    		}
    	});
    }

    public void PanelChange(JPanel jp, String str){
    	System.out.println(jp.getName());
        String name = jp.getName();

        if(name==PanelNames[0]){
        	spanel = (StartPanel)jp;
            spanel.setVisible(false);
        }
        else if(name==PanelNames[1]){
            lpanel = (LoadPanel)jp;
            lpanel.setVisible(false);
        }
        else if(name==PanelNames[2]){
            gpanel = (GamePanel)jp;
            gpanel.setVisible(false);

            contentPane.removeAll();
        }

        if(str==PanelNames[0]){
        	this.add(spanel);
            spanel.setVisible(true);
        }
        else if(str==PanelNames[1]){
        	this.add(lpanel);
            lpanel.setVisible(true);
        }
        else if(str==PanelNames[2]){
        	gpanel = new GamePanel(this,PanelNames[1]);
        	this.add(gpanel);
            gpanel.setVisible(true);

            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            contentPane.setLayout(new BorderLayout(0, 0));
            setContentPane(contentPane);

            gpanel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    positionField.setText(e.getPoint().toString());
                    gpanel.processClick(e.getPoint());
                }
            });

            contentPane.add(gpanel, BorderLayout.CENTER);

            JPanel operationPanel = new JPanel();
            contentPane.add(operationPanel, BorderLayout.SOUTH);

            positionField = new JTextField();
            positionField.setHorizontalAlignment(SwingConstants.RIGHT);
            positionField.setEditable(false);
            operationPanel.add(positionField);
            positionField.setColumns(16);

            gpanel.gameStart();
        }
    }
}
