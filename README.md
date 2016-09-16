Final Project Proposal
Note: Since I am still looking for teammates to form a group, and I don’t know what exactly my teammates will be interested in, so I am listing 2 project proposals and will eventually pick one of them. 
Team: 	Wu, Qingjun
Karkada Nakshathri, Athreya
Padhy, Umakant

Project 1: 
Title: WeChat like system
Summary: WeChat is an Instant Messenger like WhatsApp, it is very popular especially in Chinese community. It is a combination of Instant Message and Social Network. This system will include both UI and backend. 
Functionality: 
1.	User Registration: register a new user;
2.	User Login/Log out;
3.	Add/Remove/block/unblock contacts (friends);
4.	Send/Receive Message;
5.	Create/Delete/Join/Quit group chatting;
6.	Send/Receive message in group chat;
7.	Moments board: this is significant feature that makes WeChat different from traditional IM and similar to social media like Facebook/Twitter;
8.	OpenID identity provider API; This is like other social network Facebook/Google+/Twitter, whose account can be used to register/login other websites.
Key Technologies/Components of the system:
1.	Message/post type: text, audio, video;
2.	Backend Database will maintain contact list of user/group;
3.	System will maintain IP/Port address of the user;
4.	User can send message directly to another user using UDP protocol; it doesn’t need server to dispatch it;
5.	When user sends a message, a message will be sent to the server – this is the way the system keeps track of chat history;
6.	Privacy: a friend of mine is not my friend if we’re not explicitly connected; My post in Moments board can be seen by my contact, by cannot be seen by friends of my contact if we’re not connected;
7.	Message is ordered: when a user is sending two messages to another, these two message should arrive in order; 

Project 2: 
Title: Tiny URL System
Project Summary:   The system will have look like bit.ly, which maps long URL into short URL presented by 6 alphabet numbers.  The system will be mainly focusing on the back end; there would be GUI, but it will be just simple UI. 
Functionality: 
1.	Create(): Given a link to a web site, generate a short url for it;
2.	MapFromShortToLong(): Given a short URL find out the original long URL;
3.	MapFromLongToShort(): Given a long URL find out the corresponding short URL;
4.	Redirect: when the system receives a short URL request, it will redirect the long URL web site;
5.	Delete(): delete short URL from the system. 
Key Technologies/Components of the System: 
1.	62-base encoding is used to encode a short 6 alphabet numeric  URL to integer;  
2.	Two database table schema will be defined: |short|long|, and |long|short|;
3.	DB Sharding: this can be implanted using a few database with the same schema, e.g., db00, db02, …, db09;  given a short URL like “aBc123”, convert it into integer and modulus 10, get R, the short URL will be found in db0R; 
4.	Cache will be used. Will define a cache class for this. It’s basically a key-value map.

