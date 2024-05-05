export interface AccountDetails {
  accountId:            string;
  balance:              number;
  currentPage:          number;
  totalPages:           number;
  pageSize:             number;
  accountOperationDTOS: AccountOperationPage[];
}

export interface BankAccount {
  id:            string;
  balance:       number;
  createdAt:     Date;
  status:        string;
  type:          string;
}

export interface AccountOperationPage {
  id:            number;
  operationDate: Date;
  amount:        number;
  type:          string;
  description:   null;
}
