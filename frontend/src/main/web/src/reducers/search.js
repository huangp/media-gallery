import { handleActions } from 'redux-actions'
import {UPDATE_SEARCH_TERM, RESOURCE_SUCCESS} from '../actions/GeneralSearchActions'

const defaultState = {
  term: '',
  resources: []
}

const search = handleActions(
    {
      [UPDATE_SEARCH_TERM]: (state, action) => {
        // TODO use immutable js or something
        return {...state, term: action.payload}
      },
      [RESOURCE_SUCCESS]: (state, action) => {
        return {...state, resources: action.payload}
      }
    }, defaultState)

export default search
