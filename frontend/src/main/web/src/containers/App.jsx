import React, { Component } from 'react'
import { connect } from 'react-redux'
import Helmet from 'react-helmet'


const App = (props) => {
  const { children, pageTitle} = props

  return (
    <div>
      <Helmet
        title="Home"
        titleTemplate="%s | Gallery"
      />
      {children}
    </div>
  )
}

function mapStateToProps (state, ownProps) {
  return {
    activePath: ownProps.location.pathname
  }
}

export default connect(mapStateToProps)(App)
