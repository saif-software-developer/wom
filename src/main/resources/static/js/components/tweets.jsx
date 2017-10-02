import React from 'react';
import Tweet from './tweet.jsx';

class Tweets extends React.Component{
    
    constructor() {
        super();
        this.state = {
            tweets: [],
            stompClient: null
        };
        this.connect = this.connect.bind(this);
        this.disconnect=this.disconnect.bind(this);
        this.replyTo=this.replyTo.bind(this);
    }
               
    setConnected(connected) {
        document.getElementById('connect').disabled = connected;
        document.getElementById('disconnect').disabled = !connected;
        document.getElementById('conversationDiv').style.visibility 
          = connected ? 'visible' : 'hidden';
    }
     
    connect() {
        var socket = new SockJS('/wom-websocket');
        var stompClient = Stomp.over(socket);  
        stompClient.connect({}, function(frame) {
            this.setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/tweets/new', function(messageOutput) {
                console.log('new cars feed : ' + messageOutput.body);
                this.showMessageOutput(JSON.parse(messageOutput.body));
            }.bind(this));
            stompClient.subscribe('/topic/tweets/used', function(messageOutput) {
                console.log('used cars feed : ' + messageOutput.body);
                this.showMessageOutput(JSON.parse(messageOutput.body));
            }.bind(this));
        }.bind(this));
        this.setState({ stompClient: stompClient });
    }
     
    disconnect() {
        var stompClient = this.state.stompClient;
        if(stompClient != null) {
            stompClient.disconnect();
        }
        this.setState({ stompClient: stompClient });
        this.setConnected(false);
        console.log("Disconnected");
    }
     
    replyTo() {
        var from = document.getElementById('from').value;
        var text = document.getElementById('text').value;
        var stompClient = this.state.stompClient;
        stompClient.send("/app/reply-to", {}, 
          JSON.stringify({'from':from, 'text':text}));
    }
     
    showMessageOutput(messageOutput) {        
        var tweets = this.state.tweets;
        tweets.push(messageOutput);
        this.setState({ tweets: tweets });
        console.log('showMessageOutput : ' + messageOutput);
    }
    
    render() {
        var tweets=this.state.tweets || [];
        var tweetsUI=[];
        tweetsUI.push(
            <div key="tweetsController" id="conversationDiv">
                <input type="text" id="from" placeholder="Choose a nickname"/>
                <button id="connect" onClick={() => this.connect()}>Connect</button>
                <button id="disconnect" disabled="disabled" onClick={() => this.connect()}>
                    Disconnect
                </button>
            </div>
        );
        for (var tweetIndex=0;tweetIndex<tweets.length;tweetIndex++) {
            tweetsUI.push(
                <Tweet key={'tweetId'+ tweetIndex} tweet={tweets[tweetIndex]}/>
            );
        }
        return (
          <div key="tweets" className="tweets">
            {tweetsUI}
          </div>
        );
    }
}

export default Tweets;
