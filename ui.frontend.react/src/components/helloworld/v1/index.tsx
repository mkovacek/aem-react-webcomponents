import React, {Component} from 'react';

// @ts-ignore
import {byAttrVal, byJsonAttrVal, createCustomElement, DOMModel} from "@adobe/react-webcomponent";
import MetaUtils from '../../../utils/MetaUtils';
import {User} from './User';

class HelloWorldModel extends DOMModel {
  @byAttrVal() title: string = '';
  @byAttrVal() text: string = '';
  @byAttrVal() message: string = '';
  @byAttrVal() updateapi: string = '';
  @byJsonAttrVal() users: Array<User> = [];
  isInEditor = MetaUtils.isInEditor();
  hidePlaceHolder = false;
}

class HelloWorldComponent extends Component<HelloWorldModel> {
  render() {

    return (

        <div className='cmp-helloworld' data-cmp-is='helloworld'>
          <h2 className='cmp-helloworld__title'>{this.props.title}</h2>

          {this.props?.text.length > 0 &&
              <>
                <p className='cmp-helloworld__item-label'>Text property:</p>
                <pre className='cmp-helloworld__item-output'
                     data-cmp-hook-helloworld='property'>{this.props.text}</pre>
              </>
          }

          {this.props?.message.length > 0 &&
              <>
                <p className='cmp-helloworld__item-label'>Model message:</p>
                <pre className='cmp-helloworld__item-output'
                     data-cmp-hook-helloworld='model'>{this.props.message}</pre>
              </>
          }

          {this.props?.updateapi.length > 0 &&
              <>
                <p className='cmp-helloworld__item-label'>Update API:</p>
                <pre className='cmp-helloworld__item-output'
                     data-cmp-hook-helloworld='model'>{this.props.updateapi}</pre>
              </>
          }

          {this.props?.users.length > 0 &&
              <>
                <p className="cmp-helloworld__item-label">Users:</p>
                <ul>
                  {this.props.users.map(user => (<li>{user.lastname} {user.firstname}</li>))}
                </ul>
              </>
          }
        </div>
    )
  }
}

const ButtonCustomElement = createCustomElement(HelloWorldComponent, HelloWorldModel, 'element');
// @ts-ignore
window.customElements.define('aem-helloworld', ButtonCustomElement);