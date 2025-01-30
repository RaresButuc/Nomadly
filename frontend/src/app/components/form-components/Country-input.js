"use client";

import axios from "axios";
import Select from "react-select";
import { useState, useEffect } from "react";

import DefaultURL from "@/app/usefull/DefaulURL";
import ChangeLink from "@/app/usefull/ChangeLink";
import FirstLetterUppercase from "@/app/usefull/FirstLetterUppercase";
import LanguageLocationOptionLabel from "@/app/usefull/LanguageLocationOptionLabel";

export default function CountrySelect({ user, article }) {
  const [allCountries, setAllCountries] = useState(null);

  useEffect(() => {
    const fetchCountries = async () => {
      try {
        const response = await axios.get(`${DefaultURL}/location`);
        const dataCountries = response.data.map((location) => ({
          value: location.id,
          label: (
            <LanguageLocationOptionLabel
              cca2={location.cca2}
              value={FirstLetterUppercase(location.country)}
            />
          ),
        }));

        setAllCountries(dataCountries);
      } catch (error) {
        console.error("Error fetching countries:", error);
      }
    };

    fetchCountries();
  }, [article, user]);

  return allCountries ? (
    <Select
      name="countryInput"
      ref={ref}
      onChange={(e) =>
        ref
          ? ChangeLink(
              "country",
              e.label.props.value.split(" ")[0].charAt(0).toLowerCase() +
                e.label.props.value.split(" ")[0].slice(1)
            )
          : null
      }
      options={allCountries}
      defaultValue={{
        label: article ? (
          article.location ? (
            <LanguageLocationOptionLabel
              cca2={article.location.cca2}
              value={FirstLetterUppercase(article.location.country)}
            />
          ) : (
            "Select A Suitable Location For Article"
          )
        ) : user ? (
          <LanguageLocationOptionLabel
            cca2={user.location.cca2}
            value={FirstLetterUppercase(user.location.country)}
          />
        ) : (
          "Select a Country"
        ),
        value: article
          ? article.location
            ? article.location.id
            : null
          : user
          ? user.location.id
          : null,
      }}
      menuPortalTarget={document.body}
      styles={{
        menuPortal: (base) => ({ ...base, zIndex: 9999 }),
        control: (baseStyles, state) => ({
          ...baseStyles,
          borderColor: state.isFocused ? "grey" : "yellow",
        }),
      }}
    />
  ) : null;
}
