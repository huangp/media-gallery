import { handleActions } from 'redux-actions'
import {UPDATE_SEARCH_TERM} from '../actions/searchActions'

const defaultState = {
  term: ''
}

const search = handleActions({
  [UPDATE_SEARCH_TERM]: (state, action) => {
    // TODO use immutable js or something
    return {...state, term: action.payload}
  }
}, defaultState)

export default search
