# Twilio

This program uses Twilio, Google Analytics, and Heroku to play voice messages to a user when they call in from any number.

When a user calls the designated phone number, a message plays asking them to press a dialpad number to choose between one of X "bedtime stories". After the number is pressed, the corresponding bedtime story plays. After the story finishes, the user can press "1" to receive a text message containing a link to the bedtime story heard, or "0" to go back to the menu of stories (this also happens if no action is taken).

Data is sent to Google Analytics every time a particular story is chosen, if the story is finished, and if it is downloaded.
