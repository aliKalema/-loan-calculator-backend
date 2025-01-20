package co.ke.resilient.loan_calculator.exceptions;

public class Exceptions {
    public static class EmailException extends RuntimeException {
        public EmailException(String message) {
            super(message);
        }
    }

    public static class MailAttachmentException extends RuntimeException {
        public MailAttachmentException(String message) {
            super(message);
        }
    }

    public static class MailEmbededException extends RuntimeException {
        public MailEmbededException(String message) {
            super(message);
        }
    }

    public static class EmployeeNotFoundException extends RuntimeException {
        public EmployeeNotFoundException(String format) {
        }
    }

    public static class TenantNotFoundException extends RuntimeException {
        public TenantNotFoundException(String format) {
        }
    }

    public static class EmployeeExistsException extends RuntimeException {
        public EmployeeExistsException(String format) {
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String format) {
        }
    }

    public static class AccessTokenUnavailableException extends RuntimeException {
        public AccessTokenUnavailableException(String message) {
            super(message);
        }
    }

    public static class FailedB2CTransationException extends RuntimeException {
        public  FailedB2CTransationException(String message) {
            super(message);
        }
    }

    public static class UnknownTransationException extends RuntimeException {
        public  UnknownTransationException(String message) {
            super(message);
        }
    }

    public static class EmptyEmployeeListException extends RuntimeException {
        public  EmptyEmployeeListException(String message) {
            super(message);
        }
    }

    public static class UserExistException extends RuntimeException {
        public  UserExistException(String message) {
            super(message);
        }
    }

    public static class TransactionNotFoundException extends RuntimeException {
        public  TransactionNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidTokenException extends RuntimeException {
        public  InvalidTokenException(String message) {
            super(message);
        }
    }

    public static class AppExistsException extends RuntimeException {
        public  AppExistsException(String message) {
            super(message);
        }
    }

    public static  class DivisionNotFoundException  extends RuntimeException{
        public  DivisionNotFoundException(String message) {
            super(message);
        }
    }

    public static  class NameExistException  extends RuntimeException{
        public  NameExistException(String message) {
            super(message);
        }
    }

    public static  class EmailNotFoundException  extends RuntimeException{
        public  EmailNotFoundException(String message) {
            super(message);
        }
    }

    public static  class DepartmentNotFoundException  extends RuntimeException{
        public  DepartmentNotFoundException(String message) {
            super(message);
        }
    }

    public static  class LeaveTypeNotFoundException  extends RuntimeException{
        public  LeaveTypeNotFoundException(String message) {
            super(message);
        }
    }

    public static  class InvalidLeaveRequestFoundException  extends RuntimeException{
        public  InvalidLeaveRequestFoundException(String message) {
            super(message);
        }
    }

    public static class LeaveTypeAlreadyAssignedToEmployee extends RuntimeException {
        public LeaveTypeAlreadyAssignedToEmployee(String message) {
            super(message);
        }
    }

    public static class NextOfKInNotFoundException extends RuntimeException {
        public NextOfKInNotFoundException(String message) {
            super(message);
        }
    }

    public static class EducationalDetailsNotFoundException extends RuntimeException {
        public EducationalDetailsNotFoundException(String message) {
            super(message);
        }
    }

    public static class WorkHistoryNotFoundException extends RuntimeException {
        public WorkHistoryNotFoundException(String message) {
            super(message);
        }
    }

    public static class DuplicateDeductionException extends RuntimeException {
        public DuplicateDeductionException(String message) {
            super(message);
        }
    }

    public static class DuplicateSalaryTemplateException extends RuntimeException {
        public DuplicateSalaryTemplateException(String message) {
            super(message);
        }
    }

    public static class DeductionNotFoundExceptions extends RuntimeException {
        public DeductionNotFoundExceptions(String message) {
            super(message);
        }
    }

    public static class EarningNotFoundExceptions extends RuntimeException {
        public EarningNotFoundExceptions(String message) {
            super(message);
        }
    }

    public static class DuplicatePayrollEmployeeException extends RuntimeException {
        public DuplicatePayrollEmployeeException(String format) {
        }
    }
}
