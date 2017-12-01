import React, { Component } from 'react';
import PropTypes from 'prop-types';
import TableComponent from './../components/table-component.jsx';
import { withStyles } from 'material-ui/styles';
import IconButton from 'material-ui/IconButton';
import Icon from 'material-ui/Icon';
import Input, { InputLabel } from 'material-ui/Input';
import { FormControl } from 'material-ui/Form';
import ReactAudioPlayer from 'react-audio-player';
import Grid from 'material-ui/Grid';
import PopupComponent from './../components/popup-component.jsx';
import GeluidUploaden from './../containers/geluid-uploaden.jsx';

const styles = theme => ({
  container: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  formControl: {
    margin: theme.spacing.unit,
  },
});

class WeetjesBeheren extends Component {

  state = {
    search: '',
    addOpen: false,
  };


  render() {

    const { classes } = this.props;

    const headers = [
      { text: 'Weetje' },
      { text: 'Afspelen' }
    ];

    const data = [
      {
        key: 'knieen weetje',
        children: [
          {
            children:
            "Een olifant heeft slechts 2 knieen",
            key: 'olifant'
          },
          {
            children:
            <ReactAudioPlayer
              src="http://www.wavsource.com/snds_2017-09-17_1751672946049674/animals/elephant.wav"
              controls
            />,
            key: 'unique key'
          }
        ]
      },
      {
        key: 'lion row',
        children: [
          {
            children:
            "Leeuwen zijn cool!",
            key: 'lion'
          },
          {
            children:
            <ReactAudioPlayer
              src="http://www.wavsource.com/snds_2017-09-17_1751672946049674/animals/lion_roar.wav"
              controls
            />,
            key: 'lion player'
          }
        ]
      },
    ];

    return (
      <div>
        <h1>Weetjes beheren</h1>
        <Grid container spacing={24}>
          <Grid item xs={12}>
            <div>
              <FormControl className={classes.formControl}>
                <InputLabel htmlFor="search-simple">Zoeken</InputLabel>
                <Input id="search-simple" value={this.state.search} onChange={(event) => this.setState({ search: event.target.value })} />
              </FormControl>
              <IconButton onClick={() => this.setState({ addOpen: true })}>
                <Icon>add_circle</Icon>
              </IconButton>
            </div>
          </Grid>
          <Grid item xs={12}>
            <TableComponent data={data} headers={headers} />
          </Grid>
        </Grid>

        {this.state.addOpen &&
          <PopupComponent title={"Weetje toevoegen"} open={this.state.addOpen} onRequestClose={() => this.setState({ addOpen: false })}>
            <GeluidUploaden identifier="Weetje "/>
          </PopupComponent>
        }
      </div>
    );
  }
}

PopupComponent.propTypes = {
  classes: PropTypes.object,
};

export default withStyles(styles, { withTheme: true })(WeetjesBeheren);

