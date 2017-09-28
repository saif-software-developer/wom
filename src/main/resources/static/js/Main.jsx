import React from 'react';
import ReactDOM from 'react-dom';
class Main extends React.Component {

    constructor() {
        super();
        this.state = {
        	tweets : []
        };
    }
    componentDidMount() {
        fetch('http://localhost:8080/wom/newCars')
            .then(result=>result.json())
            .then(tweets=>this.setState({tweets}));
    }
    render() {
        var tweets=this.state.tweets;
        console.log("Rendering models, number of models available : " + tweets.length);
        var tweetsUI=[];

        for(var index=0;index<tweets.length;index++){
            tweetsUI.push(tweets[index]);
        }

        return (
            <div>
               Hello Twitter consumers 
               {tweetsUI}
            </div>
        );
    }
}



ReactDOM.render(
    <Main />,
    document.getElementById('container')
);