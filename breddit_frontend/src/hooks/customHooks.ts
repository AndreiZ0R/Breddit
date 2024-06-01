import {MutableRefObject, useEffect, useState} from "react";

const useIsVisible = (ref: MutableRefObject<HTMLInputElement | null>) => {
    const [isIntersecting, setIntersecting] = useState<boolean>(false);

    useEffect(() => {
        if (ref && ref.current) {

            const observer = new IntersectionObserver(([entry]) => {
                    setIntersecting(entry.isIntersecting)
                }
            );

            observer.observe(ref.current);
            return () => {
                observer.disconnect();
            };
        }
    }, [ref]);

    return isIntersecting;
}

// USAGE:
// const firstStepRef = useRef<HTMLInputElement | null>(null);
// const firstStepVisible = useIsVisible(firstStepRef);
//
// const secondStepRef = useRef<HTMLInputElement | null>(null);
// const secondStepVisible = useIsVisible(secondStepRef);
//
// const thirdStepRef = useRef<HTMLInputElement | null>(null);
// const thirdStepVisible = useIsVisible(thirdStepRef);

export {useIsVisible};