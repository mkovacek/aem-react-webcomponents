import React from 'react';

import universal from 'react-universal-component';

const withProps = (Component: any, props: any) => {
  return function (matchProps: any) {
    return <Component {...props} {...matchProps} />
  }
};

const ErrorMessage = () => {
  return (
      <div>Error loading chunks!</div>
  )
};

const ComponentBlockSkeleton = (props: any) => (
    <div className="loading-skeleton loading-skeleton__block"
         style={{height: props.skeletonHeight ? props.skeletonHeight : '50px'}}/>
);

const FallbackComponent = withProps(ComponentBlockSkeleton, {skeletonHeight: 1000});

export const withAsyncImport = (asyncImport: any,
    loadingComponent = FallbackComponent) =>
    universal(asyncImport, {
      loading: loadingComponent,
      error: ErrorMessage,
      ignoreBabelRename: false
    });

export default withAsyncImport;