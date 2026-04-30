import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

    public class BasicGameApp implements Runnable, KeyListener, MouseListener {
        //step one for key and mouse

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
        private Astro Astro;
        private Asteroid[] asteroids;
        private Alien Alien;
        //private Asteroid asteroid1;
        //variables to call in code


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
            astroPic = Toolkit.getDefaultToolkit().getImage("astroDude.jpg");
            //load the picture //picture for specific character
            asteroidPic = Toolkit.getDefaultToolkit().getImage("asteroid.jpg");
            backgroundPic = Toolkit.getDefaultToolkit().getImage("spaceBackground.jpg");
            alienPic = Toolkit.getDefaultToolkit().getImage("Alienship.jpg");
            //setting images to their specific pic
            Astro = new Astro(WIDTH / 2, HEIGHT / 2);
            asteroids = new Asteroid[3];
            //asteroid array to set how many asteroids I want
            for (int x = 0; x < asteroids.length;x++){
                asteroids[x] = new Asteroid((int)(Math.random()*1000),(int)(Math.random()*650));
                //for loop so array can run
                System.out.println();

            }
            //asteroid1 = new Asteroid(20,25);
            //asteroid1.dx = -asteroid1.dx;
            asteroids = new Asteroid[4];
            //another array for asteroids to print 4 asteroids
            for (int x = 0; x < asteroids.length; x++) {
                asteroids[x] = new Asteroid((int) (Math.random() * 1000), (int) (Math.random() * 700));
                //another for loop for the second array to work

            }
            Alien = new Alien(10,10);



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
            Astro.move();
            //asteroid1.move();
            Alien.move();
            crashing();
            //make methods run
            for (int i = 0; i < asteroids.length; i++) {
                asteroids[i].move();
                //make for loop to make asteroids move
            }

        }

        public void crashing() {
            if (Alien.hitbox.intersects(Astro.hitbox) && Alien.isCrashing == false) {
                //if statement for alien intersecting with astro kills astro
                System.out.println("explode!");
                Alien.height += 50;
                Alien.height = Alien.height + 50;
                //Alien expands in height when it interacts with astro
                Alien.isCrashing = true;
                Alien.dy = -Alien.dy;
                Astro.dy = -Astro.dy;
                Astro.isAlive = false;
                //makes Astro die when intersects with Alien
            }
            //if (Alien.hitbox.intersects(Astro.hitbox)) {
              //  System.out.println("no intersection");
            //}
            for (int x = 0; x < asteroids.length; x++) {
                if (asteroids[x].hitbox.intersects(Astro.hitbox)) {
                    System.out.println("asteroid crash");
                    Astro.isAlive = false;
                    //astro also dies when it intersects with asteroids
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
             canvas.addKeyListener(this);
            canvas.addMouseListener(this);
            //step 2 for key and mouse to work
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
            if (Astro.isAlive == true) {
                g.drawImage(astroPic, Astro.xpos, Astro.ypos, Astro.width, Astro.height, null);
                //draw astro
            }
            if (Alien.isAlive == true) {
                g.drawImage(astroPic, Alien.xpos, Alien.ypos, Alien.width, Alien.height, null);
                g.drawRect(Astro.hitbox.x, Astro.hitbox.y, Astro.hitbox.width, Astro.hitbox.height);
                //draw astro hitbox
            }
            //g.setColor(Color.GREEN);
          //  g.drawImage(asteroidPic, asteroid1.xpos, asteroid1.ypos, asteroid1.width, asteroid1.height, null);
            g.drawImage(alienPic, Alien.xpos, Alien.ypos, Alien.width, Alien.height, null);
            //draw alien
            //g.drawRect(100, 300, 200, 200);
           // g.drawRect(Astro.hitbox.x, Astro.hitbox.y, Astro.hitbox.width, Astro.hitbox.height);
            //g.drawRect(astro2.hitbox.x, astro2.hitbox.y, astro2.hitbox.width, astro2.hitbox.height);
           // g.drawRect(asteroid1.hitbox.x, asteroid1.hitbox.y, asteroid1.hitbox.width, asteroid1.hitbox.height);
            //g.drawRect(Alien.hitbox.x, Alien.hitbox.y, Alien.hitbox.width, Alien.hitbox.height);

            for (int z = 0; z < asteroids.length; z++) {
                g.drawImage(asteroidPic, asteroids[z].xpos, asteroids[z].ypos, asteroids[z].width, asteroids[z].height, null);
                //for loop draws asteroid
            }
            g.dispose();

            bufferStrategy.show();
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println(e.getPoint());
            Alien.xpos = e.getX();
            Alien.ypos = e.getY();
            //when I click with my mouse, the alien spawns there
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("entered!!!");
            //prints entered when mouse enters the game
            Astro.dx = 5;
            Astro.dy = 5;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println("exited");
            //prints exited when mouse leaves the game
            Astro.dx = 5;
            Astro.dy = 5;
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Key typed" + e.getKeyCode());

            if (e.getKeyCode() == 38) {
                //up arrow
                System.out.println("Pressed up arrow");
                Astro.dy = -Math.abs(Astro.dy);
                Astro.dy = -2;
            }
            if (e.getKeyCode() == 40) {
                //down arrow
                System.out.println("pressed down arrow");
                Astro.dy = Math.abs(Astro.dy);
                Astro.dy = 2;
            }
            if (e.getKeyCode() == 37) {
                //left arrow
                System.out.println("pressed left arrow");
                Astro.dx = -Math.abs(Astro.dx);
            }
            if (e.getKeyCode() == 39) {
                //right arrow
                System.out.println("pressed right arrow");
                Astro.dx = Math.abs(Astro.dx);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //step 3 for key and mouse
            System.out.println("Key typed " + e.getKeyCode());
            if (e.getKeyCode() == 38) {
                //up arrow
                System.out.println(" not pressed up arrow");
                Astro.dy = -Math.abs(Astro.dy);
                //Astro.dy = 0;
            }
            System.out.println("Key typed " + e.getKeyCode());
            if (e.getKeyCode() == 40) {
                //down arrow
                System.out.println(" not pressed down arrow");
                Astro.dy = Math.abs(Astro.dy);
                //Astro.dy = 0;
            }
            System.out.println("Key typed " + e.getKeyCode());
            if (e.getKeyCode() == 39) {
                //right arrow
                System.out.println("pressed right arrow");
                Astro.dx = Math.abs(Astro.dx);
            }
            System.out.println("Key typed " + e.getKeyCode());
            if (e.getKeyCode() == 37) {
                //left arrow key
                System.out.println("pressed left arrow");
                Astro.dx = -Math.abs(Astro.dx);
            }
        }
    }