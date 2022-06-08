import React, {Component, MouseEvent} from 'react';
import ButtonV1, {
  ButtonV1Model
} from "@adobe/aem-core-components-react-base/dist/authoring/button/v1/ButtonV1";
// @ts-ignore
import {byAttrVal, createCustomElement, DOMModel} from "@adobe/react-webcomponent";
import MetaUtils from '../../../utils/MetaUtils';

class ButtonModel extends DOMModel implements ButtonV1Model{
  @byAttrVal text?: string;
  @byAttrVal link?: string;
  @byAttrVal icon?: string;
  isInEditor = MetaUtils.isInEditor();
  hidePlaceHolder = false;
}

class ReactButton extends Component<ButtonModel> {

  handleOnClick(event:MouseEvent){
    console.log("event", event);
  }

  render() {

    //const ButtonV1 = withAsyncImport(() => import(/* webpackChunkName: "ButtonV1" */ '@adobe/aem-core-components-react-base/dist/authoring/button/v1/ButtonV1'));

    return (

        <ButtonV1 {...this.props}
                  handleOnClick={this.handleOnClick}
        />
    )
  }
}
const ButtonCustomElement = createCustomElement(ReactButton, ButtonModel, "element");
// @ts-ignore
window.customElements.define("aem-button", ButtonCustomElement);