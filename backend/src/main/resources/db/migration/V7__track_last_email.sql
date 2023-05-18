create table last_email_sent (
  id bigint primary key,
  late_loan_date timestamp null,
  loan_read_notification_date timestamp null,
  booking_available_date timestamp null,
  constraint fk_last_email_send_user foreign key (id) references user(id)
);

INSERT INTO last_email_sent(id, late_loan_date, loan_read_notification_date, booking_available_date)
    select id, null, null, null from user;
