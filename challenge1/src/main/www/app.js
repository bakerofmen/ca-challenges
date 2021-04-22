
// Bring in the React framework
const React = require('react');
const ReactDOM = require('react-dom');

import 'bootstrap/dist/css/bootstrap.min.css';
import {
    Button,
    ButtonGroup,
    ButtonToolbar,
    Col,
    Container,
    FormControl,
    InputGroup,
    Row,
    Table,
} from 'react-bootstrap'
import './main.css';
import sortIcon from './sorticon.png';

// The tutorial had a custom JSON handler, but I want to use window.fetch
//const client = require('./client');

// Top level component
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
		    users: [],
		    totalPages: 0,
		    totalElements: 0,
		    pageNumber: 1,
		    rowsPerPage: 10,
		    lastName: "",
		    age: "",
		    sort: ""
		};
	}

	componentDidMount() {
	    this.queryUsers();
	}

    // merge a given state into the App state and requery
	queryUsers (state={}) {
	    // prepare out next state
	    var nextState = {
	        ...this.state,
	        ...state
	    }

        // query the data. Very important to match case-sensitive API here
	    fetch('/users?' + new URLSearchParams({
            // TODO sort
            // force page between 1 and totalPages, then subtract 1
	        page: Math.min(Math.max(1, +nextState.pageNumber), nextState.totalPages||1)-1,
            // force size between 10 and totalElements
	        size: Math.max(Math.min(+nextState.rowsPerPage, nextState.totalElements), 10),
            // use "" as a fallback for lastName
            lastname: nextState.lastName || "",
            // force age to be positive or else use "" as a fallback for age
            age: Math.max(0, +nextState.age) || "",
            // sort
            sort: nextState.sort || ""
	    }), {
	        method: 'GET',
	    }).then((resp) => {
            // handle a successful response
	        if (resp.ok) {
	            resp.json().then(body => {
                    nextState.users = body.content;
                    nextState.totalPages = body.totalPages;
                    nextState.totalElements = body.totalElements;
                    this.setState(nextState);
	            });
	        }

            // handle a failed response
	        else {
	            console.error("Failed to fetch users. Non-OK response code", resp);
	        }
	    }, (err) => {
            // handle a lack of response
            console.error("Failed to fetch users. Fetch error", err);
	    })
	}

	render() {
		return (
            <Container fluid>
              <TableHeadNavRow updateState={this.queryUsers.bind(this)}/>
              <UserTable users={this.state.users} updateState={this.queryUsers.bind(this)}/>
            </Container>
		)
	}
}

class UserTable extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
		    sortField: null,
		    direction: null
		};
	}

	incrementDirection(direction) {
	    if (direction == 'asc') {
	        return 'desc';
	    } else if (direction == 'desc') {
	        return null;
	    } else {
	        return 'asc';
	    }
	}

    sortBy(newField) {
        let newDir = 'asc';
        if (this.state.sortField == newField) {
            newDir = this.incrementDirection(this.state.direction);
            if (newDir) {
                // if we're already sorted on this field, then switch directions
                this.props.updateState({sort: newField + ',' + newDir});
            } else {
                // if we're already descending, just unsort
                newField = null;
                this.props.updateState({sort: null});
            }
        } else {
            // we weren't sorted on this field before, so do it ascending
            this.props.updateState({sort: newField + ',asc'});
        }

        this.setState({sortField: newField, direction: newDir});
    }

	render() {
		const users = this.props.users.map(user =>
			<User key={user.id} user={user}/>
		);
		return (
			<Table striped bordered hover size="sm">
				<tbody>
					<tr>
						<th className="sortable" onClick={() => this.sortBy('name')}> Name <img src={sortIcon} style={{ height: '0.8em' }} /></th>
						<th className="sortable" onClick={() => this.sortBy('age')}>Age <img src={sortIcon} style={{ height: '0.8em' }} /></th>
						<th>Address</th>
					</tr>
					{users}
				</tbody>
			</Table>
		)
	}
}

class User extends React.Component {
    render() {
        return (
            <tr>
              <td>{this.props.user.name}</td>
              <td>{this.props.user.age} years old</td>
              <td>{this.props.user.address1 + ", " + this.props.user.address2}</td>
            </tr>
        )
    }
}

class TableHeadNavRow extends React.Component {
    render() {
        return (
            <Row>
                <Col md={3}>
                    <AnInput onChange={(e) => this.props.updateState({pageNumber: e.target.value})}
                             label="Page number" placeholder={1} />
                </Col>
                <Col md={3}>
                    <AnInput onChange={(e) => this.props.updateState({rowsPerPage: e.target.value})}
                             label="Rows per page" placeholder={10} />
                </Col>
                <Col md={3}>
                    <AnInput onChange={(e) => this.props.updateState({age: e.target.value})} label="Age" />
                </Col>
                <Col md={3}>
                    <AnInput onChange={(e) => this.props.updateState({lastName: e.target.value})} label="Last name" />
                </Col>
            </Row>
        )
    }
}

class AnInput extends React.Component {
    render() {
        return (
            <InputGroup>
                <InputGroup.Prepend>
                    <InputGroup.Text id="btnGroupAddon">{this.props.label}</InputGroup.Text>
                </InputGroup.Prepend>
                <FormControl type="text" placeholder={this.props.placeholder} onChange={this.props.onChange}/>
            </InputGroup>
        )
    }
}

// overwrite the 'react' div with our App
ReactDOM.render(
	<App />,
	document.getElementById('react')
)
