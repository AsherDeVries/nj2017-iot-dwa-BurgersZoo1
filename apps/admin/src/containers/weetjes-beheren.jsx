import React, {Component} from 'react';
import PropTypes from 'prop-types';
import TableComponent from './../components/table-component.jsx';
import {connect} from 'react-redux';
import {withStyles} from 'material-ui/styles';
import Button from "material-ui/Button";
import Icon from 'material-ui/Icon';
import Input, {InputLabel} from 'material-ui/Input';
import {FormControl} from 'material-ui/Form';
import ReactAudioPlayer from 'react-audio-player';
import Grid from 'material-ui/Grid';
import _ from 'lodash';
import {BASE_URL} from './../constants/endpoint-constants.js';


import PopupComponent from './../components/popup-component.jsx';
import GeluidUploaden from './../components/geluid-uploaden.jsx';
import {FILEUPLOAD_ACTION_TYPES} from "../constants/actionTypes";
import styles from './../styles/style';
import {fetchWeetjes, addWeetje} from './../actions/weetjesActions';
import {uploadSound, setUploadStateEmpty} from "./../actions/uploadGeluidActions";

class WeetjesBeheren extends Component {

  state = {
    search: '',
    addOpen: false,
  };

  componentWillMount() {
    this.props.fetchWeetjes();
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.uploads.files && nextProps.uploads.files.files.length > this.props.uploads.files.files.length && nextProps.uploads.uploadStatus === FILEUPLOAD_ACTION_TYPES.UPLOAD_STATUS_SUCCESS) {
      this.props.addWeetje(nextProps.uploads.files.beschrijving, nextProps.uploads.files.files[0]);
      this.props.setUploadStateEmpty();
      this.setState({addOpen: false})
    }
  }

  onRequestClose(){
    this.setState({addOpen: false})
  }

  render() {

    const {classes} = this.props;

    const headers = [
      {text: "Beschrijving"},
      {text: "Player"},
    ];

    let results = [];

    if (this.state.search != '') {
      results = _.filter(this.props.weetjes, obj => obj.beschrijving.toLowerCase().includes(this.state.search.toLowerCase()));
    } else {
      results = this.props.weetjes;
    }

    const data = _.map(results, weetje => {
      return {
        key: weetje.id,
        children: [
          {children: weetje.beschrijving},
          {
            children:
            <ReactAudioPlayer
              src={`${BASE_URL}${weetje.bestandspad}`}
              controls
            />,
            key: `${weetje.id} player`
          }
        ]
      };
    });

    return (
      <div>
        <h1>Weetjes beheren</h1>

        <Grid container spacing={24}>
          <Grid item xs={12}>
            <div>
              <FormControl className={classes.formControl}>
                <InputLabel htmlFor="search-simple">Zoeken</InputLabel>
                <Input id="search-simple" value={this.state.search}
                       onChange={(event) => this.setState({search: event.target.value})}/>
              </FormControl>

              <Button
                className={classes.button}
                raised
                style={{"float":"right", backgroundColor: "#7ecb20", color: "white"}}
                onClick={() => this.setState({addOpen: true})}
              >
                  <Icon style={{"paddingRight": "10px"}} className={classes.rightIcon}>add_circle</Icon> Weetje toevoegen
              </Button>
            </div>
          </Grid>
          <Grid item xs={12}>
            <TableComponent data={data} headers={headers}/>
          </Grid>
        </Grid>

        {this.props.uploads.uploadStatus === FILEUPLOAD_ACTION_TYPES.UPLOAD_STATUS_IDLE && this.state.addOpen &&
        <PopupComponent title={"Weetje toevoegen"} open={this.state.addOpen} onRequestClose={this.onRequestClose.bind(this)}>
          <GeluidUploaden
            identifier="Weetje "
            uploadSound={this.props.uploadSound}
          />
        </PopupComponent>
        }

      </div>
    );
  }
}

WeetjesBeheren.propTypes = {
  classes: PropTypes.object,
  weetjes: PropTypes.arrayOf(PropTypes.object),
  fetchWeetjes: PropTypes.func,
  addWeetje: PropTypes.func,
  uploadSound: PropTypes.func,
  setUploadStateEmpty: PropTypes.func
};


function mapStateToProps(state) {
  return {
    weetjes: state.weetjesReducer.weetjes,
    uploads: state.fileUploadReducer
  };
}

export default connect(mapStateToProps, {
  fetchWeetjes,
  addWeetje,
  uploadSound,
  setUploadStateEmpty
})(withStyles(styles, {withTheme: true})(WeetjesBeheren));


