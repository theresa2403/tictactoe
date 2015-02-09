package com.goodyear.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    private boolean player1 = true;
    private String[] fields_player1 = new String[10];
    private String[] fields_player2 = new String[10];
    private int numberOfMoves = 0;
    private Button[] buttons = new Button[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.x / 4;
        int[] view_buttons = new int[]{R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8,R.id.button9};
        for (int view_button : view_buttons) {
            Button button = (Button) findViewById(view_button);
            button.setMinHeight(height);
            button.setMinWidth(height);
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, height - 100);
        }

        buttons = new Button[]{(Button)findViewById(R.id.button1),(Button)findViewById(R.id.button2),(Button)findViewById(R.id.button3),
                (Button)findViewById(R.id.button4),(Button)findViewById(R.id.button5),(Button)findViewById(R.id.button6),
                (Button)findViewById(R.id.button7),(Button)findViewById(R.id.button8),(Button)findViewById(R.id.button9)};
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeSettings (View view) {
        System.out.println("Change Settings");
    }

    private boolean checkWinner() {
        if(player1) {
            if((fields_player1[1] != null && fields_player1[2] != null && fields_player1[3] != null) ||
                    (fields_player1[4] != null && fields_player1[5] != null && fields_player1[6] != null) ||
                    (fields_player1[7] != null && fields_player1[8] != null && fields_player1[9] != null) ||
                    (fields_player1[1] != null && fields_player1[4] != null && fields_player1[7] != null) ||
                    (fields_player1[2] != null && fields_player1[5] != null && fields_player1[8] != null) ||
                    (fields_player1[3] != null && fields_player1[6] != null && fields_player1[9] != null) ||
                    (fields_player1[1] != null && fields_player1[5] != null && fields_player1[9] != null) ||
                    (fields_player1[3] != null && fields_player1[5] != null && fields_player1[7] != null) ) {
                return true;
            }
        } else {
            if((fields_player2[1] != null && fields_player2[2] != null && fields_player2[3] != null) ||
                    (fields_player2[4] != null && fields_player2[5] != null && fields_player2[6] != null) ||
                    (fields_player2[7] != null && fields_player2[8] != null && fields_player2[9] != null) ||
                    (fields_player2[1] != null && fields_player2[4] != null && fields_player2[7] != null) ||
                    (fields_player2[2] != null && fields_player2[5] != null && fields_player2[8] != null) ||
                    (fields_player2[3] != null && fields_player2[6] != null && fields_player2[9] != null) ||
                    (fields_player2[1] != null && fields_player2[5] != null && fields_player2[9] != null) ||
                    (fields_player2[3] != null && fields_player2[5] != null && fields_player2[7] != null) ) {
                return true;
            }
        }
        return false;
    }

    public void clickButton2 (View view) {
        method_clickButton2(view);
    }

    private void method_clickButton2(View view) {
        numberOfMoves ++;
        SharedPreferences sharedPref = getApplication().getSharedPreferences(getString(R.string.preference_settings), Context.MODE_PRIVATE);
        if(sharedPref.getBoolean(getString(R.string.settings_multiplayer), true)) {
            TextView player = (TextView) findViewById(R.id.player);
            Button current_button = (Button) findViewById(view.getId());
            int buttonId = Integer.parseInt(view.getResources().getResourceName(view.getId()).toString().split("button")[1]);
            if(player1) {
                current_button.setText("X");
                fields_player1[buttonId] = ""+buttonId;
            } else {
                current_button.setText("O");
                fields_player2[buttonId] = ""+buttonId;
            }

            if(checkWinner()) {
                player.setText("Gewonnen");
                for (Button button : buttons) {
                    button.setOnClickListener(restartListener);
                }
            } else if(numberOfMoves == 9) {
                player.setText("Unentschieden");
            } else {
                if(player1) {
                    player.setText("Player 2");
                    player.setTextColor(Color.BLUE);
                } else {
                    player.setText("Player 1");
                    player.setTextColor(Color.RED);
                }
                // change between true and false
                player1^=true;
                // remove onClick Listener
                current_button.setOnClickListener(null);
            }
        } else {
            Button current_button = (Button) findViewById(view.getId());
            int buttonId = Integer.parseInt(view.getResources().getResourceName(view.getId()).toString().split("button")[1]);
            current_button.setText("X");
            fields_player1[buttonId] = ""+buttonId;
            TextView player = (TextView) findViewById(R.id.player);
            if(checkWinner()) {
                player.setText("Gewonnen");
                int[] view_buttons = new int[]{R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8,R.id.button9};
                for (Button button : buttons) {
                    button.setOnClickListener(restartListener);
                }
            } else if(numberOfMoves == 9) {
                player.setText("Unentschieden");
            } else {
                current_button.setOnClickListener(null);
                computersMove(view, sharedPref.getString(getString(R.string.settings_mode), "EASY"));
            }


        }

    }

    private void computersMove(View view, String mode) {
        if(mode.equals("EASY")) {
            randomField(view);
        } else {
            if(numberOfMoves == 1) {
                if(mode.equals("MIDDLE")) {
                    randomField(view);
                } else if(mode.equals("DIFFICULT")) {
                    if(buttons[6].getText().equals("X") || buttons[0].getText().equals("X") || buttons[2].getText().equals("X") || buttons[8].getText().equals("X")  ) {
                        writeComputersButton(4);
                    } else {
                        writeComputersButton(6);
                    }
                }
            } else {
                int number = 0;
                boolean hasNumber = false;
                if(buttons[0].getText().equals("X") && buttons[1].getText().equals("X") && !buttons[2].getText().equals("O")) {
                    number = 2;
                    hasNumber = true;
                } else if(buttons[0].getText().equals("X") && buttons[2].getText().equals("X") && !buttons[1].getText().equals("O")) {
                    number = 1;
                    hasNumber = true;
                } else if(buttons[1].getText().equals("X") && buttons[2].getText().equals("X") && !buttons[0].getText().equals("O")) {
                    number = 0;
                    hasNumber = true;
                } else if(buttons[3].getText().equals("X") && buttons[4].getText().equals("X") && !buttons[5].getText().equals("O")) {
                    number = 5;
                    hasNumber = true;
                } else if(buttons[3].getText().equals("X") && buttons[5].getText().equals("X") && !buttons[4].getText().equals("O")) {
                    number = 4;
                    hasNumber = true;
                } else if(buttons[4].getText().equals("X") && buttons[5].getText().equals("X") && !buttons[3].getText().equals("O")) {
                    number = 3;
                    hasNumber = true;
                } else if(buttons[6].getText().equals("X") && buttons[7].getText().equals("X") && !buttons[8].getText().equals("O")) {
                    number = 8;
                    hasNumber = true;
                } else if(buttons[6].getText().equals("X") && buttons[8].getText().equals("X") && !buttons[7].getText().equals("O")) {
                    number = 7;
                    hasNumber = true;
                } else if(buttons[7].getText().equals("X") && buttons[8].getText().equals("X") && !buttons[6].getText().equals("O")) {
                    number = 6;
                    hasNumber = true;
                } else if(buttons[0].getText().equals("X") && buttons[3].getText().equals("X") && !buttons[6].getText().equals("O")) {
                    number = 6;
                    hasNumber = true;
                } else if(buttons[0].getText().equals("X") && buttons[6].getText().equals("X") && !buttons[3].getText().equals("O")) {
                    number = 3;
                    hasNumber = true;
                } else if(buttons[3].getText().equals("X") && buttons[6].getText().equals("X") && !buttons[0].getText().equals("O")) {
                    number = 0;
                    hasNumber = true;
                } else if(buttons[1].getText().equals("X") && buttons[4].getText().equals("X") && !buttons[7].getText().equals("O")) {
                    number = 7;
                    hasNumber = true;
                } else if(buttons[1].getText().equals("X") && buttons[7].getText().equals("X") && !buttons[4].getText().equals("O")) {
                    number = 4;
                    hasNumber = true;
                } else if(buttons[4].getText().equals("X") && buttons[7].getText().equals("X") && !buttons[1].getText().equals("O")) {
                    number = 1;
                    hasNumber = true;
                } else if(buttons[2].getText().equals("X") && buttons[5].getText().equals("X") && !buttons[8].getText().equals("O")) {
                    number = 8;
                    hasNumber = true;
                } else if(buttons[5].getText().equals("X") && buttons[8].getText().equals("X") && !buttons[2].getText().equals("O")) {
                    number = 2;
                    hasNumber = true;
                } else if(buttons[2].getText().equals("X") && buttons[8].getText().equals("X") && !buttons[5].getText().equals("O")) {
                    number = 5;
                    hasNumber = true;
                } else if(buttons[0].getText().equals("X") && buttons[4].getText().equals("X") && !buttons[8].getText().equals("O")) {
                    number = 8;
                    hasNumber = true;
                } else if(buttons[0].getText().equals("X") && buttons[8].getText().equals("X") && !buttons[4].getText().equals("O")) {
                    number = 4;
                    hasNumber = true;
                } else if(buttons[4].getText().equals("X") && buttons[8].getText().equals("X") && !buttons[0].getText().equals("O")) {
                    number = 0;
                    hasNumber = true;
                } else if(buttons[2].getText().equals("X") && buttons[4].getText().equals("X") && !buttons[6].getText().equals("O")) {
                    number = 6;
                    hasNumber = true;
                } else if(buttons[2].getText().equals("X") && buttons[6].getText().equals("X") && !buttons[4].getText().equals("O")) {
                    number = 4;
                    hasNumber = true;
                } else if(buttons[4].getText().equals("X") && buttons[6].getText().equals("X") && !buttons[2].getText().equals("O")) {
                    number = 2;
                    hasNumber = true;
                }

                if(hasNumber) {
                    writeComputersButton(number);
                } else {
                    randomField(view);
                }
            }
        }

        player1 = false;
        if(checkWinner()) {
            TextView player = (TextView) findViewById(R.id.player);
            player.setTextColor(Color.BLUE);
            player.setText("Verloren");
            for (Button button : buttons) {
                button.setOnClickListener(restartListener);
            }
        }
        player1 = true;
        numberOfMoves ++;
    }

    private void randomField(View view) {
        int[] view_buttons = new int[]{R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8,R.id.button9};
        while(true) {
            int random = (int) (10*Math.random() -1);
            Button button = (Button) findViewById(view_buttons[random]);
            if(button.getText() == null || button.getText().equals("") || button.getText() == "") {
                writeComputersButton(random);
                break;
            }
        }
    }

    private void writeComputersButton(int number) {
        buttons[number].setText("O");
        buttons[number].setOnClickListener(null);
        int buttonId = number+1;
        fields_player2[buttonId] = ""+buttonId;
    }

    private void method_restart(View view) {
        int[] buttons = new int[]{R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8,R.id.button9};
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                method_clickButton2(view);
            }
        };

        for (int buttonId : buttons) {
            Button button = (Button) findViewById(buttonId);
            button.setText("");
            button.setOnClickListener(listener);
        }
        TextView player = (TextView) findViewById(R.id.player);
        player.setText("Player 1");
        player.setTextColor(Color.RED);
        player1 = true;
        fields_player1 = new String[10];
        fields_player2 = new String[10];
        numberOfMoves = 0;
    }

    public void restart (View view) {
        method_restart(view);
    }



    public View.OnClickListener restartListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            method_restart(view);
            // gleich nach dem Beenden eines Spiels ein neues anfangen, nicht erst leere Felder zeigen
            // nimmt Einstellungen von Settings, sind gespeichert, auch wenn Anwendung geschlossen wurde
            SharedPreferences sharedPref = getApplication().getSharedPreferences(getString(R.string.preference_settings), Context.MODE_PRIVATE);
            if(sharedPref.getBoolean(getString(R.string.settings_necessaryClicks), false)) {
                method_clickButton2(view);
            }
        }
    };

    // method for menu item "Settings"
    public boolean changeSettings (MenuItem item) {
        Intent intent = new Intent(this,DisplaySettingsActivity.class);
        startActivity(intent);
        return true;
    }


}
