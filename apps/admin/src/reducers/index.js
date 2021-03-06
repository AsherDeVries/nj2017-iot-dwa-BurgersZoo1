
import { combineReducers } from 'redux';
import sessionReducer from './session/session-reducer';
import speurpuntReducer from './speurpunt/speurpunt-reducer';
import dierengeluidenReducer from './dierengeluiden/dierengeluiden-reducer';
import weetjesReducer from './weetjes/weetjes-reducer';
import potenReducer from './poten/poten-reducer';
import verblijvenReducer from './verblijven/verblijven-reducer';
import { routerReducer } from 'react-router-redux';
import fileUploadReducer from './fileupload/fileupload-reducer';


const rootReducer = combineReducers({
  routing: routerReducer,
  sessionReducer,
  speurpuntReducer,
  dierengeluidenReducer,
  weetjesReducer,
  potenReducer,
  verblijvenReducer,
  fileUploadReducer
});

export default rootReducer;
