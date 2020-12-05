# Redux
[Redux前瞻: Flux框架](http://www.ruanyifeng.com/blog/2016/01/flux.html)

[Redux 入门教程（一）：基本用法](http://www.ruanyifeng.com/blog/2016/09/redux_tutorial_part_one_basic_usages.html)

[Redux 入门教程（二）：中间件与异步操作](http://www.ruanyifeng.com/blog/2016/09/redux_tutorial_part_two_async_operations.html)

[Redux 入门教程（三）：React-Redux 的用法](http://www.ruanyifeng.com/blog/2016/09/redux_tutorial_part_three_react-redux.html)

## Glossary
### State
```JavaScript
type State = any
```
State (also called the state tree) is a broad term, but in the Redux API it usually refers to **the single state value that is managed by the store** and returned by getState(). It represents the entire state of a Redux application, which is often a deeply nested object.

By convention, the top-level state is an object or some other key-value collection like a Map, but technically it can be any type. Still, you should do your best to keep the state serializable. Don't put anything inside it that you can't easily turn into JSON.

### Action
```JavaScript
type Action = Object
```
An action is a plain object that represents **an intention to change the state**. **Actions are the only way to get data into the store.** Any data, whether from UI events, network callbacks, or other sources such as WebSockets needs to eventually be dispatched as actions.

Actions must have a type field that indicates the type of action being performed. Types can be defined as constants and imported from another module. It's better to use strings for type than Symbols because strings are serializable.

### Reducer
```JavaScript
type Reducer<S, A> = (state: S, action: A) => S
```
**A reducer (also called a reducing function) is a function that accepts an accumulation and a value and returns a new accumulation.** They are used to reduce a collection of values down to a single value.

Reducers are not unique to Redux—they are a fundamental concept in functional programming. Even most non-functional languages, like JavaScript, have a built-in API for reducing. In JavaScript, it's Array.prototype.reduce().

In Redux, the accumulated value is the state object, and the values being accumulated are actions. Reducers calculate a new state given the previous state and an action. They must be pure functions—functions that return the exact same output for given inputs. They should also be free of side-effects. This is what enables exciting features like hot reloading and time travel.

Do not put API calls into reducers.

### Dispatching Function
```JavaScript
type BaseDispatch = (a: Action) => Action
type Dispatch = (a: Action | AsyncAction) => any
```
A dispatching function (or simply dispatch function) is a function that accepts an action or an async action; it then may or may not dispatch one or more actions to the store.

We must distinguish between dispatching functions in general and the base dispatch function provided by the store instance without any middleware.

The base dispatch function always synchronously sends an action to the store's reducer, along with the previous state returned by the store, to calculate a new state. It expects actions to be plain objects ready to be consumed by the reducer.

Middleware wraps the base dispatch function. It allows the dispatch function to handle async actions in addition to actions. Middleware may transform, delay, ignore, or otherwise interpret actions or async actions before passing them to the next middleware. 

### Other Glossary
[Glossary](https://redux.js.org/understanding/thinking-in-redux/glossary)

### Router
[Router](https://www.educative.io/edpresso/what-is-a-react-router)

### Provider
The <Provider /> makes the Redux store available to any nested components that have been wrapped in the connect() function.

Since any React component in a React Redux app can be connected, most applications will render a <Provider> at the top level, with the entire app’s component tree inside of it.

Normally, you can’t use a connected component unless it is nested inside of a <Provider>.

### Lifecycle function
[Lifecycle function](https://www.cnblogs.com/gdsblog/p/7348375.html)

## Process of Implementing Redux
![Redux_2](https://github.com/LarryNaaa/Technical-Summary/blob/master/Image/Redux_2.png)

![Redux_1](https://github.com/LarryNaaa/Technical-Summary/blob/master/Image/Redux_1.png)

![Redux_3](https://github.com/LarryNaaa/Technical-Summary/blob/master/Image/Redux_3.jpg)

1. When UI Event triggers (OnClick, OnChange, etc) in `Component`, `Action` will be called based on the event.
2. `Action` will fetch data from back end and be dispatched based on its type.
3. The corresponding `Reducer` will receive `Action` and return new State into `Store`.
4. Connect the `Component` to the `Store`, so when `State` was changed in `Store`, the `Component` can get `State` and display it.

### `Store`
```JavaScript
import { createStore, applyMiddleware, compose } from "redux";
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const initalState = {};
const middleware = [thunk];

let store;

const ReactReduxDevTools =
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__();

if (window.navigator.userAgent.includes("Chrome") && ReactReduxDevTools) {

  store = createStore(
    rootReducer,
    initalState,
    compose(applyMiddleware(...middleware), ReactReduxDevTools)
  );
} else {
  store = createStore(
    rootReducer,
    initalState,
    compose(applyMiddleware(...middleware))
  );
}

export default store;
```
Creates a Redux store that holds the complete state tree of our app. There should only be a single store in our app.

We need to configure this `Store` with three things:
1. the `Reducer` is a `combineReducer` which can return the next state tree depending on the current state tree and actions.
2. the `preloadedState` is an empty object.
3. the `enhancer` function is `applyMiddleware(...middleware)`.

#### `combineReducers`
Create a main `Reducer` called `combineReducers`, add other small `Reducer`(which manage independent parts of the state) in it.
```JavaScript
import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import projectReducer from "./projectReducer";
import backlogReducer from "./backlogReducer";
import securityReducer from "./securityReducer";

export default combineReducers({
  errors: errorReducer,
  project: projectReducer,
  backlog: backlogReducer,
  security: securityReducer,
});
```
#### `applyMiddleware(...middleware)`
Middleware can help us to extend Redux and support asynchronous actions. We use `redux-thunk` here. It lets the action creators invert control by dispatching functions. They would receive dispatch as an argument and may call it asynchronously.

### Register Part
#### Create Register Component(View)
Create a component called `Register`, it can support users to register themselves, it is a class based component and contains some HTML(We have a form in it, so that users can input their username, password and confirm password). We need to do follow things:
1. set name on input fields
2. create a constructor, set initial state and error
3. set value on input fields
4. create onChange function, set it on each input field, bind on constructor
5. create onSubmit function, set it on the form, bind on constructor

```JavaScript
import React, { Component } from "react";
import { createNewUser } from "../../actions/securityActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";

class Register extends Component {
  constructor() {
    super();

    this.state = {
      username: "",
      fullName: "",
      password: "",
      confirmPassword: "",
      errors: {},
    };
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentDidMount() {
    if (this.props.security.validToken) {
      this.props.history.push("/dashboard");
    }
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  onSubmit(e) {
    e.preventDefault();
    const newUser = {
      username: this.state.username,
      fullName: this.state.fullName,
      password: this.state.password,
      confirmPassword: this.state.confirmPassword,
    };

    this.props.createNewUser(newUser, this.props.history);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  render() {
    const { errors } = this.state;
    return (
      <div className="register">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">Sign Up</h1>
              <p className="lead text-center">Create your Account</p>
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.fullName,
                    })}
                    placeholder="Full Name"
                    name="fullName"
                    value={this.state.fullName}
                    onChange={this.onChange}
                  />
                  {errors.fullName && (
                    <div className="invalid-feedback">{errors.fullName}</div>
                  )}
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.username,
                    })}
                    placeholder="Email Address (Username)"
                    name="username"
                    value={this.state.username}
                    onChange={this.onChange}
                  />
                  {errors.username && (
                    <div className="invalid-feedback">{errors.username}</div>
                  )}
                </div>
                <div className="form-group">
                  <input
                    type="password"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.password,
                    })}
                    placeholder="Password"
                    name="password"
                    value={this.state.password}
                    onChange={this.onChange}
                  />
                  {errors.password && (
                    <div className="invalid-feedback">{errors.password}</div>
                  )}
                </div>
                <div className="form-group">
                  <input
                    type="password"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.confirmPassword,
                    })}
                    placeholder="Confirm Password"
                    name="confirmPassword"
                    value={this.state.confirmPassword}
                    onChange={this.onChange}
                  />
                  {errors.confirmPassword && (
                    <div className="invalid-feedback">
                      {errors.confirmPassword}
                    </div>
                  )}
                </div>
                <input type="submit" className="btn btn-info btn-block mt-4" />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Register.propTypes = {
  createNewUser: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  errors: state.errors,
  security: state.security,
});
export default connect(mapStateToProps, { createNewUser })(Register);
```

#### Connect Register Component to Store
##### `connect()` function
The `connect()` function connects a React component to a Redux store.

It provides its connected component with the pieces of the data it needs from the store, and the functions it can use to dispatch actions to the store.

It returns a new, connected component class that wraps the component we passed in.

The first argument is `mapStateToProps`, the second argument is `mapDispatchToProps`.

###### `mapDispatchToProps` function
`mapDispatchToProps` is used for dispatching actions to the store and we can specify which actions our component might need to dispatch.
```JavaScript
import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

export const createNewUser = (newUser, history) => async (dispatch) => {
  try {
    await axios.post("/api/users/register", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};
```
We create a new function called `createNewUser`, when UI events trigger(OnSubmit, OnChange...), this function will be called.

###### Use `axios.post()` to interact with backend and post data to an endpoint
This method requires two parameters. First, it needs the URI of the service endpoint. Second, an object which contains the properties that we want to send to our server should be passed to it. If it is a happy path and we get our new user object from back-end, then users will be redirected to the login page.

###### Catch errors and dispatch action
Users may be fail for registration(like they register with blank username or their password and confirm password are not matched), we want to display some message on the web page. So if we get errors from back-end, we need to dispatch an action(the type of this action called `GET_ERRORS`, the payload of it is errors).

###### errorReducer
When we get errors from back-end and `GET_ERRORS` action will be dispatched, and then it will be received by a reducer. We create a `Reducer` called `errorReducer` to return the changed state(errors) into `Store`.
```JavaScript
import { GET_ERRORS } from "../actions/types";

const initialState = {};

export default function (state = initialState, action) {
  switch (action.type) {
    case GET_ERRORS:
      return action.payload;

    default:
      return state;
  }
}
```

###### `mapStateToProps` function
The changed state is used to show the updated component. This function is used for selecting the part of the data from the store that the connected component needs. It is called every time the store state changes. It receives the entire store state, and should return an object of data this component needs.
```JavaScript
const mapStateToProps = (state) => ({
  errors: state.errors,
  security: state.security,
});
```

##### `propTypes` function
It can be used to ensure that the parameters received by this component are valid.
```JavaScript
Register.propTypes = {
  createNewUser: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired,
};
```

##### lifecycle method
```JavaScript
componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }
```

### Login Part
#### Login Component
We have a form which supports users to input their username and password, so we need to complete some HTMLs. We need to set name and value on these input fileds, create a constructor and set initial state and errors, create some event functions like OnChange and OnSubmit. 
```JavaScript
import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";
import { login } from "../../actions/securityActions";

class Login extends Component {
  constructor() {
    super();
    this.state = {
      username: "",
      password: "",
      errors: {},
    };
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  // When users login successful, they cannot go to landing to register and login again
  componentDidMount() {
    if (this.props.security.validToken) {
      this.props.history.push("/dashboard");
    }
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.security.validToken) {
      this.props.history.push("/dashboard");
    }

    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  onSubmit(e) {
    e.preventDefault();
    const LoginRequest = {
      username: this.state.username,
      password: this.state.password,
    };

    this.props.login(LoginRequest);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  render() {
    const { errors } = this.state;
    return (
      <div className="login">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">Log In</h1>
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.username,
                    })}
                    placeholder="Email Address"
                    name="username"
                    value={this.state.username}
                    onChange={this.onChange}
                  />
                  {errors.username && (
                    <div className="invalid-feedback">{errors.username}</div>
                  )}
                </div>
                <div className="form-group">
                  <input
                    type="password"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.password,
                    })}
                    placeholder="Password"
                    name="password"
                    value={this.state.password}
                    onChange={this.onChange}
                  />
                  {errors.password && (
                    <div className="invalid-feedback">{errors.password}</div>
                  )}
                </div>
                <input type="submit" className="btn btn-info btn-block mt-4" />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Login.propTypes = {
  login: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
  errors: state.errors,
});

export default connect(mapStateToProps, { login })(Login);
```
#### `mapDispatchToProps`
Add an action called `login` in `securityAction`.
```JavaScript
import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

export const login = (LoginRequest) => async (dispatch) => {
  try {
    // post => Login Request
    const res = await axios.post("/api/users/login", LoginRequest);
    // extract token from res.data
    const { token } = res.data;
    // store the token in the localStorage
    localStorage.setItem("jwtToken", token);
    // set our token in header
    setJWTToken(token);
    // decode token on React
    const decoded = jwt_decode(token);
    // dispatch to our securityReducer
    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded,
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};
```
1. post Login request to backend by `axios.post` function and get user's token
2. store this token in the `localStorage`
3. set our token in the header of request with a key called "Authorization" by `setJWTToken` method
4. decode token by `jwt-decode`(a library)
5. dispatch action `SET_CURRENT_USER`
6. use a try...catch to catch errors

##### `setJWTToken` method
```JavaScript
import axios from "axios";

const setJWTToken = (token) => {
  if (token) {
    axios.defaults.headers.common["Authorization"] = token;
  } else {
    delete axios.defaults.headers.common["Authorization"];
  }
};

export default setJWTToken;
```
#### securityReducer
Create a `Reducer` called `securityReducer` and add it into the main `Reducer`(`combineReducers`). This `Reducer` will return new states into `Store`, include a boolean variable called `validToken` and user object.
```JavaScript
import { SET_CURRENT_USER } from "../actions/types";

const initialState = {
  validToken: false,
  user: {},
};

const booleanActionPayload = (payload) => {
  if (payload) {
    return true;
  } else {
    return false;
  }
};

export default function (state = initialState, action) {
  switch (action.type) {
    case SET_CURRENT_USER:
      return {
        ...state,
        validToken: booleanActionPayload(action.payload),
        user: action.payload,
      };

    default:
      return state;
  }
}
```

#### Lifecycle function
##### `componentWillReceiveProps` function
```JavaScript
componentWillReceiveProps(nextProps) {
    if (nextProps.security.validToken) {
      this.props.history.push("/dashboard");
    }

    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }
```
This function will be called when props are changed in the component. We call `MapStateToProps`  function to map two states(error and security) to props before, so props are changed and this function will be called by component. In this function, we have two 'if' conditions here:
1. if the boolean variable `validToken` is true, which means the token of user is valid and user has already login, then users will be redirected to dashboard page.
2. if we get errors from backend, show them on the web page.

##### `componentDidMount` function
```JavaScript
componentDidMount() {
    if (this.props.security.validToken) {
      this.props.history.push("/dashboard");
    }
  }
```
This function will be called after the initial render, if the boolean variable `validToken` is true, which means the token of user is valid and user has already login, then users will be redirected to dashboard page. And users will not be allowed to login or register again unless they log out.

#### Keep the token in the state of the apllication before it is expired
When we refresh or reload the web pages, token goes away. In the meeting place of all the component(which is the App.js), if we can get a valid token from `localStorage`, keep it in the state of the apllication before it is expired.
```JavaScript
const jwtToken = localStorage.jwtToken;

if (jwtToken) {
  setJWTToken(jwtToken);
  const decoded_jwtToken = jwt_decode(jwtToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decoded_jwtToken,
  });

  const currentTime = Date.now() / 1000;
  if (decoded_jwtToken.exp < currentTime) {
    //handle logout
    store.dispatch(logout());
    window.location.href = "/";
  }
}
```

##### Logout Action
When users logout or their token is expired, the token should be removed from the `localStorage` and set the payload empty.
```JavaScript
export const logout = () => (dispatch) => {
  localStorage.removeItem("jwtToken");
  setJWTToken(false);
  dispatch({
    type: SET_CURRENT_USER,
    payload: {},
  });
};
```
### Secure Routes
```JavaScript
import React from "react";
import { Route, Redirect } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";

const SecuredRoute = ({ component: Component, security, ...otherProps }) => (
  <Route
    {...otherProps}
    render={(props) =>
      security.validToken === true ? (
        <Component {...props} />
      ) : (
        <Redirect to="/login" />
      )
    }
  />
);

SecuredRoute.propTypes = {
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
});

export default connect(mapStateToProps)(SecuredRoute);
```
```JavaScript
import React, { Component } from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import AddProject from "./components/Project/AddProject";
import { Provider } from "react-redux";
import store from "./store";
import UpdateProject from "./components/Project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTask from "./components/ProjectBoard/ProjectTasks/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/ProjectTasks/UpdateProjectTask";
import Landing from "./components/Layout/Landing";
import Register from "./components/UserManagement/Register";
import Login from "./components/UserManagement/Login";
import jwt_decode from "jwt-decode";
import setJWTToken from "./securityUtils/setJWTToken";
import { SET_CURRENT_USER } from "./actions/types";
import { logout } from "./actions/securityActions";
import SecuredRoute from "./securityUtils/SecureRoute";

const jwtToken = localStorage.jwtToken;

if (jwtToken) {
  setJWTToken(jwtToken);
  const decoded_jwtToken = jwt_decode(jwtToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decoded_jwtToken,
  });

  const currentTime = Date.now() / 1000;
  if (decoded_jwtToken.exp < currentTime) {
    //handle logout
    store.dispatch(logout());
    window.location.href = "/";
  }
}

// React-Redux 提供Provider组件，可以让容器组件拿到state。
// 使用React-Router的项目，与其他项目没有不同之处，
// 也是使用Provider在Router外面包一层，
// 毕竟Provider的唯一功能就是传入store对象。

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Header />
            {
              //Public Routes
            }

            <Route exact path="/" component={Landing} />
            <Route exact path="/register" component={Register} />
            <Route exact path="/login" component={Login} />

            {
              //Private Routes
            }
            <Switch>
              <SecuredRoute exact path="/dashboard" component={Dashboard} />
              <SecuredRoute exact path="/addProject" component={AddProject} />
              <SecuredRoute
                exact
                path="/updateProject/:id"
                component={UpdateProject}
              />
              <SecuredRoute
                exact
                path="/projectBoard/:id"
                component={ProjectBoard}
              />
              <SecuredRoute
                exact
                path="/addProjectTask/:id"
                component={AddProjectTask}
              />
              <SecuredRoute
                exact
                path="/updateProjectTask/:backlog_id/:pt_id"
                component={UpdateProjectTask}
              />
            </Switch>
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;

```






