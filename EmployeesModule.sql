--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 11.5

-- Started on 2022-02-20 05:14:31

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 202 (class 1259 OID 20163)
-- Name: addresses; Type: TABLE; Schema: public; Owner: itk
--

CREATE TABLE public.addresses (
    id integer NOT NULL,
    country character varying(45) NOT NULL,
    street character varying(90) NOT NULL,
    zip_code character varying(45) NOT NULL,
    city character varying(45) NOT NULL,
    state character varying(45) NOT NULL
);


ALTER TABLE public.addresses OWNER TO itk;

--
-- TOC entry 201 (class 1259 OID 20161)
-- Name: Addresses_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Addresses_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Addresses_id_seq" OWNER TO itk;

--
-- TOC entry 2883 (class 0 OID 0)
-- Dependencies: 201
-- Name: Addresses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Addresses_id_seq" OWNED BY public.addresses.id;


--
-- TOC entry 199 (class 1259 OID 20137)
-- Name: benefits; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.benefits (
    id integer NOT NULL,
    name character varying(45) NOT NULL,
    description text NOT NULL,
    availability boolean NOT NULL,
    by_law boolean NOT NULL
);


ALTER TABLE public.benefits OWNER TO itk;

--
-- TOC entry 198 (class 1259 OID 20135)
-- Name: Benefits_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Benefits_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Benefits_id_seq" OWNER TO itk;

--
-- TOC entry 2884 (class 0 OID 0)
-- Dependencies: 198
-- Name: Benefits_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Benefits_id_seq" OWNED BY public.benefits.id;


--
-- TOC entry 197 (class 1259 OID 20129)
-- Name: employees; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employees (
    id integer NOT NULL,
    name character varying(45) NOT NULL,
    last_name character varying(45) NOT NULL,
    date_of_birth date NOT NULL,
    email character varying(45) NOT NULL,
    telephone_number character varying(45) NOT NULL,
    hiring_date date NOT NULL,
    work_email character varying(45) NOT NULL,
    active boolean NOT NULL,
    "position" character varying(45) NOT NULL,
    gender character varying(45) NOT NULL,
    type_of_element character varying(45) NOT NULL,
    address_id integer NOT NULL,
    unique_key character varying(45) NOT NULL
);


ALTER TABLE public.employees OWNER TO itk;

--
-- TOC entry 196 (class 1259 OID 20127)
-- Name: Employees_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Employees_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Employees_id_seq" OWNER TO itk;

--
-- TOC entry 2885 (class 0 OID 0)
-- Dependencies: 196
-- Name: Employees_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Employees_id_seq" OWNED BY public.employees.id;


--
-- TOC entry 204 (class 1259 OID 20281)
-- Name: authorities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.authorities (
    authority_id integer NOT NULL,
    authority character varying(45) NOT NULL
);


ALTER TABLE public.authorities OWNER TO itk;

--
-- TOC entry 203 (class 1259 OID 20279)
-- Name: authorities_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.authorities_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.authorities_id_seq OWNER TO itk;

--
-- TOC entry 2886 (class 0 OID 0)
-- Dependencies: 203
-- Name: authorities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.authorities_id_seq OWNED BY public.authorities.authority_id;


--
-- TOC entry 200 (class 1259 OID 20146)
-- Name: employee_benefits; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employee_benefits (
    employee_id integer NOT NULL,
    benefit_id integer NOT NULL
);


ALTER TABLE public.employee_benefits OWNER TO itk;

--
-- TOC entry 206 (class 1259 OID 20289)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    username character varying(45) NOT NULL,
    password character varying(60) NOT NULL,
    enabled boolean NOT NULL,
    employee_id integer
);


ALTER TABLE public.users OWNER TO itk;

--
-- TOC entry 207 (class 1259 OID 20300)
-- Name: users_authorities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_authorities (
    user_id integer NOT NULL,
    authority_id integer NOT NULL
);


ALTER TABLE public.users_authorities OWNER TO itk;

--
-- TOC entry 205 (class 1259 OID 20287)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO itk;

--
-- TOC entry 2887 (class 0 OID 0)
-- Dependencies: 205
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 2720 (class 2604 OID 20166)
-- Name: addresses id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.addresses ALTER COLUMN id SET DEFAULT nextval('public."Addresses_id_seq"'::regclass);


--
-- TOC entry 2721 (class 2604 OID 20284)
-- Name: authorities authority_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authorities ALTER COLUMN authority_id SET DEFAULT nextval('public.authorities_id_seq'::regclass);


--
-- TOC entry 2719 (class 2604 OID 20140)
-- Name: benefits id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.benefits ALTER COLUMN id SET DEFAULT nextval('public."Benefits_id_seq"'::regclass);


--
-- TOC entry 2718 (class 2604 OID 20132)
-- Name: employees id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees ALTER COLUMN id SET DEFAULT nextval('public."Employees_id_seq"'::regclass);


--
-- TOC entry 2722 (class 2604 OID 20292)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 2872 (class 0 OID 20163)
-- Dependencies: 202
-- Data for Name: addresses; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.addresses (id, country, street, zip_code, city, state) VALUES (1, 'Mexico', 'Calle 86c #934 por 200 y 217A  opichen', '97356', 'merida', 'yucatan');
INSERT INTO public.addresses (id, country, street, zip_code, city, state) VALUES (2, 'Estados Unidos', 'Calle 86c #934 por 200 y 217A  opichen', '97356', 'Merida', 'Yucatan');
INSERT INTO public.addresses (id, country, street, zip_code, city, state) VALUES (3, 'Estados Unidos', 'Calle 86c #934 por 200 y 217A  opichen', '97356', 'Merida', 'Yucatan');
INSERT INTO public.addresses (id, country, street, zip_code, city, state) VALUES (15, 'Estados Unidos', 'Calle 86c #934 por 200 y 217A  opichen', '97356', 'Merida', 'Yucatan');
INSERT INTO public.addresses (id, country, street, zip_code, city, state) VALUES (16, 'Estados Unidos', 'Calle 86c #934 por 200 y 217A  opichen', '97356', 'Merida', 'Yucatan');
INSERT INTO public.addresses (id, country, street, zip_code, city, state) VALUES (18, 'Estados Unidos Mexicanos', 'Calle 86c #934 por 200 y 217A  opichen', '97356', 'Ciduad Nueva', 'Yucatan');
INSERT INTO public.addresses (id, country, street, zip_code, city, state) VALUES (20, 'Estados Unidos Mexicanos', 'Calle 86c #934 por 200 y 217A  opichen', '97356', 'Ciduad Nueva', 'Yucatan');
INSERT INTO public.addresses (id, country, street, zip_code, city, state) VALUES (22, 'Mexico', 'Calle 86c #934 por 200 y 217A  opichen', '97356', 'merida', 'yucatan');


--
-- TOC entry 2874 (class 0 OID 20281)
-- Dependencies: 204
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.authorities (authority_id, authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.authorities (authority_id, authority) VALUES (2, 'ROLE_USER');


--
-- TOC entry 2869 (class 0 OID 20137)
-- Dependencies: 199
-- Data for Name: benefits; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.benefits (id, name, description, availability, by_law) VALUES (2, 'BonusUpdatedTTT', 'employee bonus updated', true, true);
INSERT INTO public.benefits (id, name, description, availability, by_law) VALUES (3, 'Vacations', 'employee vacations', true, false);
INSERT INTO public.benefits (id, name, description, availability, by_law) VALUES (6, 'Aguinaldo', 'Aguinaldo del empleado', true, true);
INSERT INTO public.benefits (id, name, description, availability, by_law) VALUES (7, 'Beneficio de prueba ola', 'Beneficio de prueba omg funciona', true, true);
INSERT INTO public.benefits (id, name, description, availability, by_law) VALUES (8, 'Dia extra', 'Editaddo', true, true);
INSERT INTO public.benefits (id, name, description, availability, by_law) VALUES (9, 'Juegos', 'Juegos para los empleadossss', true, true);


--
-- TOC entry 2870 (class 0 OID 20146)
-- Dependencies: 200
-- Data for Name: employee_benefits; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.employee_benefits (employee_id, benefit_id) VALUES (8, 8);
INSERT INTO public.employee_benefits (employee_id, benefit_id) VALUES (8, 7);
INSERT INTO public.employee_benefits (employee_id, benefit_id) VALUES (3, 3);
INSERT INTO public.employee_benefits (employee_id, benefit_id) VALUES (10, 8);
INSERT INTO public.employee_benefits (employee_id, benefit_id) VALUES (2, 3);


--
-- TOC entry 2867 (class 0 OID 20129)
-- Dependencies: 197
-- Data for Name: employees; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.employees (id, name, last_name, date_of_birth, email, telephone_number, hiring_date, work_email, active, "position", gender, type_of_element, address_id, unique_key) VALUES (10, 'Shaid', 'Bojorquez', '1997-02-02', 'shaid.int@gmail.com', '9999999999', '2021-02-02', 'aldo.aguilar@theksqauregroup.com', true, 'ENGINEER1', 'MALE', 'INTERNAL', 22, 'BOPR971001HQRLRG05');
INSERT INTO public.employees (id, name, last_name, date_of_birth, email, telephone_number, hiring_date, work_email, active, "position", gender, type_of_element, address_id, unique_key) VALUES (1, 'Carlos', 'Reyes', '1997-02-02', 'carlos.reyes@gmail.com', '9999999999', '2021-02-02', 'carlos.reyes@theksquaregroup.com', true, 'ENGINEER1', 'MALE', 'INTERNAL', 1, 'MAHJ280603MSPRRV09');
INSERT INTO public.employees (id, name, last_name, date_of_birth, email, telephone_number, hiring_date, work_email, active, "position", gender, type_of_element, address_id, unique_key) VALUES (3, 'Guillermo', 'Ceme', '1998-01-01', 'guillermo.ceme@gmail.com', '9999999999', '2020-01-01', 'guillermo.ceme@theksqauregroup.com', true, 'ENGINEER2', 'MALE', 'EXTERNAL', 3, 'ROVI490617HSPDSS05');
INSERT INTO public.employees (id, name, last_name, date_of_birth, email, telephone_number, hiring_date, work_email, active, "position", gender, type_of_element, address_id, unique_key) VALUES (2, 'Julio', 'Vargas', '1998-01-01', 'julio.vargas@quaregroup.com', '9999999999', '2022-03-03', 'julio.vargas@theksquaregroup.com', true, 'ENGINEER2', 'FEMALE', 'INTERNAL', 16, 'PERC561125MSPRMT03');
INSERT INTO public.employees (id, name, last_name, date_of_birth, email, telephone_number, hiring_date, work_email, active, "position", gender, type_of_element, address_id, unique_key) VALUES (8, 'Fernando', 'Casasnovas', '1996-01-01', 'fernando.casasnovas@gmail.com', '9999999992', '2022-01-01', 'fernando.casa@theksqauregroup.com', true, 'ENGINEER2', 'MALE', 'INTERNAL', 20, 'MAHJ280603MSPRRV01');
INSERT INTO public.employees (id, name, last_name, date_of_birth, email, telephone_number, hiring_date, work_email, active, "position", gender, type_of_element, address_id, unique_key) VALUES (6, 'MAPS', 'Casasnovas', '1996-01-01', 'fernando.casasnovas@gmail.com', '9999999992', '2022-01-01', 'fer.casas@theksqauregroup.com', true, 'ENGINEER2', 'MALE', 'INTERNAL', 18, 'AOYA981213HQRRHL00');


--
-- TOC entry 2876 (class 0 OID 20289)
-- Dependencies: 206
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (user_id, username, password, enabled, employee_id) VALUES (1, 'Aldo', '$2a$10$HvaYtM/XwAMwyJ/jrAcq6.73GwbK8qJ5mXWeQsuu9QXi0lSOZp9v.', true, 1);
INSERT INTO public.users (user_id, username, password, enabled, employee_id) VALUES (2, 'Guillermo', '$2a$10$X7T4iN2qbh5.BTdZu2FszuTqsFLn4bhECaGpdWXHrt2KK9bz7Z9Ey', true, 3);


--
-- TOC entry 2877 (class 0 OID 20300)
-- Dependencies: 207
-- Data for Name: users_authorities; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users_authorities (user_id, authority_id) VALUES (1, 1);
INSERT INTO public.users_authorities (user_id, authority_id) VALUES (2, 2);


--
-- TOC entry 2888 (class 0 OID 0)
-- Dependencies: 201
-- Name: Addresses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Addresses_id_seq"', 22, true);


--
-- TOC entry 2889 (class 0 OID 0)
-- Dependencies: 198
-- Name: Benefits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Benefits_id_seq"', 9, true);


--
-- TOC entry 2890 (class 0 OID 0)
-- Dependencies: 196
-- Name: Employees_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Employees_id_seq"', 10, true);


--
-- TOC entry 2891 (class 0 OID 0)
-- Dependencies: 203
-- Name: authorities_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.authorities_id_seq', 1, false);


--
-- TOC entry 2892 (class 0 OID 0)
-- Dependencies: 205
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, true);


--
-- TOC entry 2730 (class 2606 OID 20168)
-- Name: addresses Addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.addresses
    ADD CONSTRAINT "Addresses_pkey" PRIMARY KEY (id);


--
-- TOC entry 2726 (class 2606 OID 20145)
-- Name: benefits Benefits_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.benefits
    ADD CONSTRAINT "Benefits_pkey" PRIMARY KEY (id);


--
-- TOC entry 2728 (class 2606 OID 20150)
-- Name: employee_benefits Employ_Benefits_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee_benefits
    ADD CONSTRAINT "Employ_Benefits_pkey" PRIMARY KEY (benefit_id, employee_id);


--
-- TOC entry 2724 (class 2606 OID 20134)
-- Name: employees Employees_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT "Employees_pkey" PRIMARY KEY (id);


--
-- TOC entry 2732 (class 2606 OID 20286)
-- Name: authorities authorities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authorities
    ADD CONSTRAINT authorities_pkey PRIMARY KEY (authority_id);


--
-- TOC entry 2734 (class 2606 OID 20316)
-- Name: users username_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT username_unique UNIQUE (username);


--
-- TOC entry 2738 (class 2606 OID 20304)
-- Name: users_authorities users_authorities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_authorities
    ADD CONSTRAINT users_authorities_pkey PRIMARY KEY (user_id, authority_id);


--
-- TOC entry 2736 (class 2606 OID 20294)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 2739 (class 2606 OID 20317)
-- Name: employees address_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT address_fk_1 FOREIGN KEY (address_id) REFERENCES public.addresses(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2744 (class 2606 OID 20310)
-- Name: users_authorities authority_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_authorities
    ADD CONSTRAINT authority_id_fk FOREIGN KEY (authority_id) REFERENCES public.authorities(authority_id);


--
-- TOC entry 2740 (class 2606 OID 20151)
-- Name: employee_benefits employees_benefits_ebfk_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee_benefits
    ADD CONSTRAINT employees_benefits_ebfk_1 FOREIGN KEY (employee_id) REFERENCES public.employees(id);


--
-- TOC entry 2741 (class 2606 OID 20156)
-- Name: employee_benefits employees_benefits_ebfk_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee_benefits
    ADD CONSTRAINT employees_benefits_ebfk_2 FOREIGN KEY (benefit_id) REFERENCES public.benefits(id);


--
-- TOC entry 2742 (class 2606 OID 20295)
-- Name: users user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_id_fk FOREIGN KEY (employee_id) REFERENCES public.employees(id);


--
-- TOC entry 2743 (class 2606 OID 20305)
-- Name: users_authorities user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_authorities
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.users(user_id);


-- Completed on 2022-02-20 05:14:32

--
-- PostgreSQL database dump complete
--

