import React, { Component } from 'react'
import { connect } from 'react-redux'
import Helmet from 'react-helmet'
import { debounce } from 'lodash'


import {searchTerm} from '../actions/searchActions'

class Videos extends Component {
  constructor () {
    super()
    // Need to add the debounce to onScroll here
    // So it creates a new debounce for each instance
    //this.onScroll = debounce(this.onScroll, 100)
  }

  render () {
    return (
      <div>This is for all things</div>
    )
  }
}

const mapStateToProps = (state) => {
  return state
}

const mapDispatchToProps = (dispatch) => {
  return {
    dispatch,
    onSearchTerm: (term) => dispatch(searchTerm(term))
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Videos)