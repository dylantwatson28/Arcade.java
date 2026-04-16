import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

    public class BasicGameApp implements Runnable, KeyListener, MouseListener {

        //Variable Definition Section
        //Declare the variables used in the program
        //Sets the width and height of the program window
        final int WIDTH = 1000;
        final int HEIGHT = 700;

        //Declare the variables needed for the graphics
        public JFrame frame;
        public Canvas canvas;
        public JPanel panel;

        public BufferStrategy bufferStrategy;
        public Image astroPic;
        public Image asteroidPic;
        public Image backgroundPic;
        public Image alienPic;

        //Declare the objects used in the program
        //These are things that are made up of more than one variable type
        private astro astro;
        private asteroid[] asteroids;
        private alien alien;
        private asteroid asteroid1;


        // Main method definition
        // This is the code that runs first and automatically
        public static void main(String[] args) {
            BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
            new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
        }


        // Constructor Method
        // This has the same name as the class
        // This section is the setup portion of the program
        // Initialize your variables and construct your program objects here.
        public BasicGameApp() {
            //
            setUpGraphics();
            //(int)(Math.random() * range);
            //range is 0-9
            //(int)(Math.random() * range) + start;
            // akes range 1-10
            int randx = (int) (Math.random() * 10) + 1;
            // make range 1 - 999
            randx = (int) (Math.random() * 999) + 1;
            int randy = (int) (Math.random() * 699) + 1;

            //variable and objects
            //create (construct) the objects needed for the game and load up
            astroPic = Toolkit.getDefaultToolkit().getImage("astroDude.png");//load the picture
            asteroidPic = Toolkit.getDefaultToolkit().getImage("astroid.jpg");
            backgroundPic = Toolkit.getDefaultToolkit().getImage("spaceBackground.jpg");
            astro = new astro(WIDTH / 2, HEIGHT / 2);
            asteroids = new asteroid[10];
            for (int x = 0; x < asteroids.length;x++){
                asteroids[x] = new asteroid();
                System.out.println();

            }
            asteroid1.dx = -asteroid1.dx;
            asteroids = new asteroid[6];
            for (int x = 0; x < asteroids.length; x++) {
                asteroids[x] = new asteroid((int) (Math.random() * 1000), (int) (Math.random() * 700));

            }


        }// BasicGameApp()


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

        // main thread
        // this is the code that plays the game after you set things up
        public void run() {

            //for the moment we will loop things forever.
            while (true) {

                moveThings();  //move all the game objects
                render();  // paint the graphics
                pause(20); // sleep for 10 ms
            }
        }


        public void moveThings() {
            //calls the move( ) code in the objects
            astro.move();
            asteroid1.move();
            alien.move();
            crashing();
            for (int i = 0; i < asteroids.length; i++) {
                asteroids[i].move();
            }

        }

        public void crashing() {
            if (asteroid1.hitbox.intersects(alien.hitbox) && asteroid1.isCrashing == false) {
                System.out.println("explode!");
                asteroid1.height += 50;
                //astroid1.height = astroid1.height + 50; another option
                asteroid1.isCrashing = true;
                asteroid1.dy = -asteroid1.dy;
                //asteroids.dy = -asteroids.dy;
                alien.isAlive = false;
            }
            if (asteroid1.hitbox.intersects(asteroid1.hitbox)) {
                System.out.println("no intersection");
            }
            for (int x = 0; x < asteroids.length; x++) {
                if (asteroids[x].hitbox.intersects(astro.hitbox)) {
                    System.out.println("astroid crash");
                    astro.isAlive = false;
                }
            }
        }

        //Pauses or sleeps the computer for the amount specified in milliseconds
        public void pause(int time) {
            //sleep
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {

            }
        }

        //Graphics setup method
        private void setUpGraphics() {
            frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

            panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
            panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
            panel.setLayout(null);   //set the layout

            // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
            // and trap input events (Mouse and Keyboard events)
            canvas = new Canvas();
            // canvas.addKeyListener(this);
            canvas.addMouseListener(this);
            canvas.setBounds(0, 0, WIDTH, HEIGHT);
            canvas.setIgnoreRepaint(true);

            panel.add(canvas);  // adds the canvas to the panel.

            // frame operations
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
            frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
            frame.setResizable(false);   //makes it so the frame cannot be resized
            frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

            // sets up things so the screen displays images nicely.
            canvas.createBufferStrategy(2);
            bufferStrategy = canvas.getBufferStrategy();
            canvas.requestFocus();
            System.out.println("DONE graphic setup");

        }
//


        //paints things on the screen using bufferStrategy
        private void render() {
            Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
            g.clearRect(0, 0, WIDTH, HEIGHT);

            g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);
            //draw the image of the astronaut
            if (astro.isAlive == true) {
                g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);
            }
            if (alien.isAlive == true) {
                g.drawImage(astroPic, alien.xpos, alien.ypos, alien.width, alien.height, null);
            }
            //g.setColor(Color.GREEN);
            g.drawImage(asteroidPic, asteroid1.xpos, asteroid1.ypos, asteroid1.width, asteroid1.height, null);
            g.drawImage(alienPic, alien.xpos, alien.ypos, alien.width, alien.height, null);
            g.fillRect(100, 300, 200, 200);
            g.drawRect(astro.hitbox.x, astro.hitbox.y, astro.hitbox.width, astro.hitbox.height);
            //g.drawRect(astro2.hitbox.x, astro2.hitbox.y, astro2.hitbox.width, astro2.hitbox.height);
            g.fillRect(asteroid1.hitbox.x, asteroid1.hitbox.y, asteroid1.hitbox.width, asteroid1.hitbox.height);
            g.fillRect(alien.hitbox.x, alien.hitbox.y, alien.hitbox.width, alien.hitbox.height);
            g.dispose();

            for (int z = 0; z < asteroids.length; z++) {
                g.drawImage(asteroidPic, asteroids[z].xpos, asteroids[z].ypos, asteroids[z].width, asteroids[z].height, null);
            }

            bufferStrategy.show();
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println(e.getPoint());
            asteroid1.xpos = e.getX();
            asteroid1.ypos = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("entered!!!");
            astro.dx = 5;
            astro.dy = 5;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println("exited");
            astro.dx = 5;
            astro.dy = 5;
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Key typed" + e.getKeyCode());

            if (e.getKeyCode() == 38) {
                System.out.println("Pressed up arrow");
                astro.dy = -Math.abs(astro.dy);
                astro.dy = -2;
            }
            if (e.getKeyCode() == 40) {
                System.out.println("pressed down arrow");
                astro.dy = Math.abs(astro.dy);
                astro.dy = 2;
            }
            if (e.getKeyCode() == 37) {
                System.out.println("pressed left arrow");
                astro.dx = -Math.abs(astro.dx);
            }
            if (e.getKeyCode() == 39) {
                System.out.println("pressed right arrow");
                astro.dx = Math.abs(astro.dx);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == 38) {
                System.out.println("pressed up arrow");
                astro.dy = -Math.abs(astro.dy);
                astro.dy = 0;
            }
            if (e.getKeyCode() == 40) {
                System.out.println("pressed down arrow");
                astro.dy = -Math.abs(astro.dy);
                astro.dy = 0;
            }
        }
    }