import React, { Component } from 'react';
import './App.css';

const PriceFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
  minimumFractionDigits: 6,
});
class App extends Component {

  state = {
    exchanges: {},
    currencies: {}
  }

  componentDidMount() {
    // Get Currency Data from backend api
    fetch('http://'+document.location.hostname+':8080/CryptoTrader/api/details')
      .then(res => res.json())
      .then((data) => {
        console.log(data)
        this.setState({ currencies: preprocess(data) })
      })
      .catch(console.log)
  }

  render() {
    return (
      <div class="container-fluid">
        <div className="page-header">
          <h1 class="p-2">CryptoTrader</h1>
        </div>
        {Object.entries(this.state.currencies).map(([k, v])=>
          <Currency name={k} value={v}></Currency>
        )}
        <div className="footer fixed-bottom">
          <h5 class="px-4">Created by <a href="mailto:dvalotia@vt.edu">Dishang Valotia</a></h5>
        </div>
      </div>
    );
  }
}

function Currency(props) {
  const exchanges = Object.entries(props.value).map(([k, v])=>
    <Exchange value={v}></Exchange>
  );

  return (
    <div class="card p-3 m-2">
      <h4 className="card-title">{props.name}</h4>
      <div>
        <div class="row flex-nowrap">
          <div class="col-md-3"><b>Exchange</b></div>
          <div class="col-md-4"><b>Buy Price</b></div>
          <div class="col-md-5"><b>Sell Price</b></div>
        </div>
        {exchanges}
      </div>
    </div>
  )
}

function Exchange(props) {
  return (
    <div class="row flex-nowrap">
      <div class="col-md-3">{props.value.name}</div>
      {props.value.recommendBuy
        ? <div class="col-md-4 recommended" title="Recommended Buy">
            {PriceFormatter.format(props.value.buyPrice)} &nbsp;
            <i className="far fa-check-circle"></i></div>
        : <div class="col-md-4">{PriceFormatter.format(props.value.buyPrice)}</div>
      }
      {props.value.recommendSell
        ? <div class="col-md-5 recommended" title="Recommended Sell">
          {PriceFormatter.format(props.value.buyPrice)} &nbsp;
          <i className="far fa-check-circle"></i></div>
        : <div class="col-md-5">{PriceFormatter.format(props.value.sellPrice)}</div>
      }
    </div>
  )
}


/**
 * Convert exchanges JSON to currencies JSON. Basically it inverts the exchanges JSON
 * @param exchanges - JSON of the format {Exchange1:{Currency1:{}, Currency2:{}}, Exchange2:{Currency1:{}, Currency2:{}}}
 * @returns {} - JSON of the format {Currency1: Exchange1:{}, Exchange2:{}, Currency2: Exchange1:{}, Exchange2:{}}
 */
function preprocess(exchanges) {
  const currencyNames = new Set();
  const currencies = {};
  // Add all currency names to a set
  Object.entries(exchanges).map(([k,v]) => Object.entries(v).map(([k,v]) => currencyNames.add(k)));

  // Invert the JSON
  currencyNames.forEach(currency => {
    if(!Object.prototype.hasOwnProperty.call(currencies, currency)){
      currencies[currency] = {};
    }
    Object.entries(exchanges).map(([k,v])=> {
      currencies[currency][k] = v[currency];
      currencies[currency][k].name = k;
    })
  })

  // Sort the final JSON
  Object.entries(currencies).map(([k,v]) => {
    const orderedExchanges = Object.keys(v).sort().reduce(
      (obj, key) => {
        obj[key] = v[key];
        return obj;
      },
      {}
    );
    currencies[k] = orderedExchanges;
  })

  return currencies;
}
export default App;