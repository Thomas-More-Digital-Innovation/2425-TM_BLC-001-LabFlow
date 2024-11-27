export interface DecodedToken {
    rol: string;
    [key: string]: any;
    // allows any other key-value pairs ([key: string]: any) to also be part of the decoded token
}
