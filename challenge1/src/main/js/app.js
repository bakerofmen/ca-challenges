
// Bring in the React framework
const React = require('react');
const ReactDOM = require('react-dom');

// The tutorial had a custom JSON handler, but I want to use window.fetch
//const client = require('./client');

// Top level component
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {users: []};
	}

	componentDidMount() {
	    fetch('/users?' + new URLSearchParams({
	        page: 0
	    }), {
	        method: 'GET',
	    }).then((resp) => {
	        if (resp.ok) {
	            resp.json().then(body => {
                    console.error("fetch returned", body);
                    this.setState({users: body.content});
	            });
	        } else {
	            console.error("Failed to fetch users. Non-OK response code", resp);
	        }
	    }, (err) => {
            console.error("Failed to fetch users. Fetch error", err);
	    })
	}

	render() {
		return (
			<UserTable users={this.state.users}/>
		)
	}
}

class UserTable extends React.Component {
	render() {
	    console.error("Rendering UserTable!", this.props);
		const users = this.props.users.map(user =>
			<User key={user.id} user={user}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Name</th>
						<th>Age</th>
						<th>Address</th>
					</tr>
					{users}
				</tbody>
			</table>
		)
	}
}

class User extends React.Component {
    render() {
        return (
            <tr>
              <td>{this.props.user.name}</td>
              <td>{this.props.user.age} years old</td>
              <td>{this.props.user.address1 + this.props.user.address2}</td>
            </tr>
        )
    }
}

// overwrite the 'react' div with our App
ReactDOM.render(
	<App />,
	document.getElementById('react')
)
