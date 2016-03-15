import { createAction } from 'redux-actions'
import { replaceRouteQuery } from '../utils/routeUtils'

import { CALL_API } from 'redux-api-middleware'

import Configs from '../constants/Configs'

import {RESOURCE_REQUEST, RESOURCE_SUCCESS, RESOURCE_FAILURE} from './GeneralSearchActions'

export const loadAllVideos = () => {
  return {
    [CALL_API]: {
      endpoint: `${Configs.baseUrl}/videos.json`,
      method: 'GET',
      types: [
        RESOURCE_REQUEST,
        {
          type: RESOURCE_SUCCESS,
          payload: (action, state, res) => {
            //const contentType = res.headers.get('Content-Type');
            //if (contentType && ~contentType.indexOf('json')) {
            //  // Just making sure res.json() does not raise an error
            //  return res.json().then((json) => normalize(json, { users: arrayOf(userSchema) }));
            //}
            return res.json().then((json) => {
              const hits = json.hits.hits
              return hits.map(hit => {
                const {_id, _source} = hit

                const subStrLength = '/home/pahuang/work/media-gallery/frontend/src/main/web/'.length
                return {
                  id: _id,
                  // TODO pahuang temporary hack

                  url: _source.file.substring(subStrLength),
                  ..._source
                }
              })
            })
          }
        },
        RESOURCE_FAILURE]
    }
  }
}
