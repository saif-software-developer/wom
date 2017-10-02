import React from 'react';

class Tweet extends React.Component {

  constructor(props) {
    super(props);
  }

  render () {
    var tweet=this.props.tweet || [];
    return (
      <article className="tweet">
        <header>
          <img className="profile" src={tweet.user.profile_image_url}/>
          <span className="name">{tweet.user.name}</span>
          <span className="screenName">@{tweet.user.screen_name}</span>
        </header>
        <p className="text">{tweet.text}</p>
      </article>
    )
  }
}

Tweet.defaultProps = {
  user: {
    name: "Lahen Ramouta",
    screenName: "LahenRza",
    imageUrl: "http://pbs.twimg.com/profile_images/875327379320836099/_URJ0pvS_normal.jpg"
  },
  text: "This is only a test of the emergency tweets system. View discression is advised. This content does not reflect the opinion of Cox Automotive"
};

export default Tweet;
