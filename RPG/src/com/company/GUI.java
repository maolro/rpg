package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI  {

    int ap = 3;
    int moveCount = 0;
    JLabel apLabel;
    JLabel hitResult;
    GameBoard map;
    board b;
    hero h;
    JPanel panelA;

    public GUI(board setBoard){

        JFrame frame = new JFrame();
        panelA = new JPanel();
        JPanel ctrlPanel = new JPanel();
        JButton moveButton = new JButton("move");
        JButton attackButton = new JButton("attack");
        JButton endTurn = new JButton("end turn");
        apLabel = new JLabel("you have "+ap+" AP");
        hitResult = new JLabel();
        map = new GameBoard(setBoard);
        b = setBoard;
        h = (hero) b.findItemsByID('P').get(0);
        panelA.add(map);
        panelA.add(hitResult);

        ctrlPanel.add(apLabel);
        ctrlPanel.add(moveButton);
        ctrlPanel.add(attackButton);
        ctrlPanel.add(endTurn);

        moveButton.addActionListener(e -> this.moveClicked(e));
        attackButton.addActionListener(e -> this.attackClicked(e));
        endTurn.addActionListener(e -> this.endTurn(e));
        panelA.setLayout(new GridLayout(0,1));
        ctrlPanel.setLayout(new GridLayout(0,1));

        frame.add(panelA, BorderLayout.CENTER);
        frame.add(ctrlPanel, BorderLayout.CENTER);
        frame.setLayout(new GridLayout(0,2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Our GUI");
        frame.pack();
        frame.setVisible(true);
    }
    public void endTurn(ActionEvent e){
        ap = 3;
        moveCount = 0;

        apLabel.setText("you have "+ap+" AP");
    }
    public void moveClicked(ActionEvent e) {
        point movePos = map.getPosition();
        point heroPos = b.findPosOfItem(h);
        if(movePos != null) {
            if(b.getAt(movePos) == null && ((heroPos.distanceTo(movePos)+moveCount)/3 <= ap)) {
                b.setAt(movePos, h);
                b.setAt(heroPos, null);
                map.repaint();
                ap -= ((heroPos.distanceTo(movePos)+moveCount)/3);
                moveCount += heroPos.distanceTo(movePos);
                apLabel.setText("you have "+ap+" AP");
            }
        }
    }
    public void attackClicked(ActionEvent e){
        point targetPos = map.getPosition();
        point heroPos = b.findPosOfItem(h);
        if(b.getAt(targetPos).id == 'Z' && ap > 1){
            enemy target = (enemy) b.getAt(targetPos);
            if(heroPos.distanceTo(b.findPosOfItem(b.findNearestTarget(heroPos, target.id)))==1){
                ap = ap-2;
                apLabel.setText("you have "+ap+" AP");
                int roll = b.diceRoll(20);
                if(roll+h.atkBonus>b.diceRoll(20)+target.atkBonus) {
                    int damage = h.damageCalc(roll);
                    if(damage > target.def)
                        damage = damage - target.def;
                    else
                        damage = 0;
                    target.hp = target.hp - damage;
                    hitResult.setText("Your attack hits and deals " + damage + " damage");
                    if(target.hp <= 0){
                        b.setAt(b.findPosOfItem(target), null);
                        map.repaint();
                    }
                }
                else
                    hitResult.setText("Your attack misses your target");
            }
        }
    }
}
class GameBoard extends JPanel implements MouseListener {
    board b;
    point currentSelected;

    public GameBoard(board setBoard) {
        b = setBoard;
        setBorder(BorderFactory.createEmptyBorder());
        this.addMouseListener(this);
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    @Override
    public JToolTip createToolTip() {
        return super.createToolTip();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw Text
        int width = this.getWidth() / b.getWidth();
        int height = this.getHeight() / b.getHeight();
        for(int j=0;j<b.getHeight();j++){
                g.drawLine(j*width,0,j*width,height*b.getHeight());
        }
        for(int j = 0; j<b.getWidth(); j++){
            g.drawLine(0,j*height,width*b.getWidth(),j*height);
        }
        FontMetrics f = g.getFontMetrics();
        for(int i = 0; i<b.getWidth(); i++){
            for(int j=0;j<b.getHeight();j++) {
                boardItem item = b.getAt(i, j);
                if (item != null) {
                    if (item.id == '+') {
                        g.fillRect(i * width, j * height, width, height);
                    } else {
                        centerText(g, height, width, f, i * width, (j + 1) * height, "" + item.id);
                    }
                }
            }
        }
        if(currentSelected!=null){
            g.setColor(Color.red);
            g.drawRect(currentSelected.getX() * width, currentSelected.getY() * height, width, height);
        }
    }
    public void centerText(Graphics g, int height, int width, FontMetrics f, int x, int y, String s){
        int xPos = (width - f.stringWidth(s))/2 + x;
        int yPos = y - (height - f.getHeight())/2;
        g.drawString(s, xPos, yPos);
    }
    public point getPosition(){
        return currentSelected;
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int width = this.getWidth() / b.getWidth();
        int height = this.getHeight() / b.getHeight();
        currentSelected = new point(x/width, y/height);
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
