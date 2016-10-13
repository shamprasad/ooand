# Final Project Proposal
##### Note: Since I am still looking for teammates to form a group, and I don’t know what exactly my teammates will be interested in, so I am listing 2 project proposals and will eventually pick one of them. 

## Team: 	
			Wu, Qingjun

			Sham Prasad PS

			Padhy, Umakant



##Title: WeChat like system

##Summary: 
WeChat is an Instant Messenger like WhatsApp, it is very popular especially in Chinese community. It is a combination of Instant Message and Social Network. This system will include both UI and backend. 

##Functionality: 

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

