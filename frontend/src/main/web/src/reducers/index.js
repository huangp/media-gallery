import { combineReducers } from 'redux'
import { routerReducer as routing} from 'react-router-redux'
import search from './search'
import {reducer as toastrReducer} from 'react-redux-toastr'

const rootReducer = (configs) => {
  return combineReducers({
    configs: () => configs,
    routing,
    toastr: toastrReducer,
    search
  })
}

export default rootReducer
