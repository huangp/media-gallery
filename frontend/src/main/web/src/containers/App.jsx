import React, { Component } from 'react'
// import a11y from 'react-a11y'
import { connect } from 'react-redux'
import Helmet from 'react-helmet'
//import { Icons } from 'zanata-ui'
//import Nav from '../components/Nav'
import View from '../components/View'

// if (process.env.NODE_ENV === 'development') a11y(React)

class App extends Component {
  constructor (props) {
    super(props)
  }
  render () {
    const theme = {
      base: {
        h: 'H(100vh)',
        fld: 'Fld(c) Fld(r)--sm'
      }
    }
    const {
      children,
      pageTitle,
      ...props
    } = this.props
    return (
      <View {...props} theme={theme}>
        <Helmet
          title="Home"
          titleTemplate="%s | Zanata"
        />
        {children}
      </View>
    )
  }
}

function mapStateToProps (state, ownProps) {
  return {
    activePath: ownProps.location.pathname
  }
}

export default connect(mapStateToProps)(App)
